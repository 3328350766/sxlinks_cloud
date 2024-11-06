package com.sxlinks.modules.system.service.dataCenter.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.domain.bo.PropertyAttrBo;
import com.sxlinks.common.domain.dto.PageReqDto;
import com.sxlinks.common.domain.dto.PageResDto;

import com.sxlinks.common.domain.dto.request.device.DeviceRtItemReqDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;
import com.sxlinks.common.domain.storage.EsMessage;
import com.sxlinks.common.metadata.BcMetaType;

import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.plugin.es.config.enums.KeyMatchTypeEnum;
import com.sxlinks.plugin.es.domain.EsRange;
import com.sxlinks.plugin.es.domain.SearchItem;
import com.sxlinks.storage.IDataCenterService;
import com.sxlinks.storage.impl.DataCenterServiceImpl;
import com.sxlinks.utils.JSONProvider;
import com.sxlinks.utils.RandomUtil;
import com.sxlinks.modules.system.entity.productCenter.ProductProperty;
import com.sxlinks.modules.system.service.dataCenter.IDeviceDataService;
import com.sxlinks.modules.system.service.productCenter.IDevicePropertyService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IProductPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  * ByteCub.cn.  * Copyright (c) 2020-2020 All Rights Reserved.  *   * @author bytecub@163.com songbin
 *  * @version Id: AdminDeviceServiceImpl.java, v 0.1 2020-12-28  Exp $$  
 */
@Slf4j
@Service
public class DeviceDataServiceImpl implements IDeviceDataService {
    @Resource
    IDataCenterService dataCenterService;
    @Autowired
    IProductPropertyService productPorpertyService;
    @Autowired
    IDeviceService deviceService;
    @Autowired
    IDevicePropertyService devicePropertyService;


    @Override
    public PageResDto<DeviceRtHistoryResDto> searchSetItem(PageReqDto<DeviceRtItemReqDto> searchPage) {
        DeviceRtItemReqDto params = searchPage.getParamData();
        List<EsRange> listRange = new ArrayList<>();
        List<SearchItem> searchItemList = new ArrayList<>();
        this.buildParam(params.getDeviceCode(), listRange, searchItemList, params);
        ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain(params.getFuncType());
        int startId = (searchPage.getPageNo() - 1) * searchPage.getLimit();
        List<DeviceRtHistoryResDto> resultPage = dataCenterService.searchDeviceSetList(params.getProductCode(), params.getDeviceCode(),
                params.getIdentifier(), typeEnum, searchItemList, startId, searchPage.getLimit(), listRange);

        long total = 0;
        if (!CollectionUtils.isEmpty(resultPage)) {
            total = dataCenterService.countDeviceSet(params.getProductCode(), params.getDeviceCode(), typeEnum,
                    params.getIdentifier(), searchItemList, listRange);
            this.processEsRtData(resultPage, params);
        }
        PageResDto<DeviceRtHistoryResDto> pageResult =
                PageResDto.genResult(searchPage.getPageNo(), searchPage.getLimit(), total, resultPage, null);
        return pageResult;

    }

    @Override
    /**
     * 获取属性的历史上报列表数据
     */
    public PageResDto<DeviceRtHistoryResDto> searchReportItem(PageReqDto<DeviceRtItemReqDto> searchPage) {
        DeviceRtItemReqDto params = searchPage.getParamData();
        List<EsRange> listRange = new ArrayList<>();
        List<SearchItem> searchItemList = new ArrayList<>();
        //搜索条件不为空的时候，赋于值
        if(searchPage.getParamData().getSearchItemList()!=null&&searchPage.getParamData().getSearchItemList().size()>0){
            searchItemList=searchPage.getParamData().getSearchItemList();
        }
        this.buildParam(params.getDeviceCode(), listRange, searchItemList, params);
        ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain(params.getFuncType());
        int startId = (searchPage.getPageNo() - 1) * searchPage.getLimit();
        List<DeviceRtHistoryResDto> resultPage = dataCenterService.searchDeviceRuntimeList(params.getProductCode(), params.getDeviceCode(),
            params.getIdentifier(), typeEnum, searchItemList, startId, searchPage.getLimit(), listRange);

        long total = 0;
        if (!CollectionUtils.isEmpty(resultPage)) {
            total = dataCenterService.countDeviceRuntime(params.getProductCode(), params.getDeviceCode(), typeEnum,
                params.getIdentifier(), searchItemList, listRange);
            this.processEsRtData(resultPage, params);
        }
        PageResDto<DeviceRtHistoryResDto> pageResult =
            PageResDto.genResult(searchPage.getPageNo(), searchPage.getLimit(), total, resultPage, null);
        return pageResult;
    }

    private void buildParam(String deviceCode, List<EsRange> rangeList, List<SearchItem> searchItemList, DeviceRtItemReqDto params) {
        EsRange range = new EsRange();
        range.setField(SXConstants.ES.HEADER_TIMESTAMP);
        if (params.getEndDate() instanceof Date || params.getStartDate() instanceof Date) {
            Long max = null == params.getEndDate() ? null : params.getEndDate().getTime();
            range.setMax(max);
            Long min = null == params.getStartDate() ? null : params.getStartDate().getTime();
            range.setMin(min);
        } else {
            range.setMax(params.getEndDate());
            range.setMin(params.getStartDate());
        }
        rangeList.add(range);

         SearchItem searchItem = new SearchItem();
         searchItem.setKey(SXConstants.ES.HEADER_DEVICE);
         searchItem.setValue(deviceCode);
         searchItem.setMatchType(KeyMatchTypeEnum.EXIST);
         searchItemList.add(searchItem);
    }

    /**
     * 对参数是boolean类型的返回值做处理
     * 因为boolean类型对true和false都有定义
     * */
    private Object processBoolean(Object value, DeviceRtItemReqDto params) {
        ProductModelTypeEnum productFuncTypeEnum = ProductModelTypeEnum.explain(params.getFuncType());
        QueryWrapper qw=new QueryWrapper<ProductProperty>();
        qw.eq("product_code",params.getProductCode());
        qw.eq("identifier",params.getIdentifier());
        ProductProperty resDto =productPorpertyService.getOne(qw);
        PropertyAttrBo attrBo= JSON.parseObject(resDto.getAttr(),PropertyAttrBo.class);
        if ((Boolean)value) {
            if (!StringUtils.isEmpty(attrBo.getBool1())) {
                value = attrBo.getBool1();
            }

        } else {
            if (!StringUtils.isEmpty(attrBo.getBool0())) {
                value = attrBo.getBool0();
            }
        }

        return value;
    }
    /**
     * 把es查询出来的原始数据根据attr等一些属性转化成最终结果
     */
    private void processEsData(List<EsMessage> resultPage, DeviceRtItemReqDto params) {
        for (EsMessage esMessage : resultPage) {
            Object value = esMessage.getRequest();
            if (null != value) {
                if (BcMetaType.BOOLEAN.getCode().equals(params.getDataType())) {
                    value = this.processBoolean(value, params);
                }
                esMessage.setRequest(value);
            }
        }
    }

    /**
     * 下行指令返回值处理
     */
    private void processEsRtData(List<DeviceRtHistoryResDto> resultPage, DeviceRtItemReqDto params) {
        for (DeviceRtHistoryResDto esMessage : resultPage) {
            Object value = esMessage.getRequest();
            if (null != value) {
                if (BcMetaType.BOOLEAN.getCode().equals(params.getDataType())) {
                    value = this.processBoolean(value, params);
                }
                esMessage.setRequest(value);
            }
        }
    }

    final private String buildDevCode() {
        return RandomUtil.randomString(16);
    }

    final private String buildDevSecret() {
        return RandomUtil.randomString(20);
    }
}
