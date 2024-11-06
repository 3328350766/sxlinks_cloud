package com.sxlinks.storage;

import com.sxlinks.common.domain.dto.PageResDto;

import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;
import com.sxlinks.common.metadata.BcMetaType;
import com.sxlinks.common.metadata.EsInsertDataBo;
import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.plugin.es.domain.EsRange;
import com.sxlinks.plugin.es.domain.PropertiesItem;
import com.sxlinks.plugin.es.domain.SearchItem;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DataCreateService.java, v 0.1 2020-12-07  Exp $$
  */

public interface IDataCenterService {

    Boolean createIndex(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier, BcMetaType bcMetaType);
    /**
     * 创建索引模版
     //* @param templateName String 模版名
     * @param indexPatterns List 命中的索引
     * @param properties Map 模版附加属性
     * @param indexAliases String 索引别名
     * @param router String 路由
     * @return true or false
     * */
    Boolean createIndexTemplate(String template, String indexAliases, String router, List<String> indexPatterns, Map<String, PropertiesItem> properties);
    /**将属性数据存数到es
     * @param data EsInsertDataBo
     * @return
     * */
    Boolean saveData(EsInsertDataBo data);
    //批量保存数据
    Boolean batchSaveData(List<EsInsertDataBo> lsData);
    /**索引删除*/
    Boolean deleteIndex(String sourceIndex);

    public Boolean deleteIndex(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier);

    List<Map> testQueryDev(String index, String devCode) throws Exception;
    <T> PageResDto<T> searchByPage(String productCode, String deviceCode, String identifier,
                                   ProductModelTypeEnum typeEnum, List<SearchItem> condition, Class<T> resClass,
                                   int startId, int size, List<EsRange> rangeList);
    /**
     * 获取设备运行数据
     *
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param condition List
     * @param   startId int
     * @param size int
     * @param rangeList List
     * @return Result<XxxxDO>
     */
     List<DeviceRtHistoryResDto> searchDeviceRuntimeList(String productCode, String deviceCode, String identifier,
                                                         ProductModelTypeEnum typeEnum, List<SearchItem> condition,
                                                         int startId, int size, List<EsRange> rangeList);
    /**
     * 获取设备运行数据总数
     *
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param condition List
     * @param rangeList List
     * @return Result<XxxxDO>
     */
    long countDeviceRuntime(String productCode, String deviceCode, ProductModelTypeEnum typeEnum, String identifier, List<SearchItem> condition, List<EsRange> rangeList);
    /**
     * 设备主动上报数据总数(事件/属性/服务调用)
     * @param productCode
     * @param typeEnum
     * @param identifier
     * @param start
     * @param end
     * @return
     * */
    long countUpRange(String productCode, ProductModelTypeEnum typeEnum, String identifier, Date start, Date end);
    /**
     * 属性设置数据总数
     * @param productCode
     * @param typeEnum
     * @param identifier
     * @param start
     * @param end
     * @return
     * */
    long countSetRange(String productCode, ProductModelTypeEnum typeEnum, String identifier, Date start, Date end);
    /**
     * 获取设备属性设置数据
     *
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param condition List
     * @param   startId int
     * @param size int
     * @param rangeList List
     * @return Result<XxxxDO>
     */
    List<DeviceRtHistoryResDto> searchDeviceSetList(String productCode, String deviceCode, String identifier,
                                                    ProductModelTypeEnum typeEnum, List<SearchItem> condition,
                                                    int startId, int size, List<EsRange> rangeList);
    /**
     * 获取设备属性设置数据总数
     *
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param condition List
     * @param rangeList List
     * @return Result<XxxxDO>
     */
    long countDeviceSet(String productCode, String deviceCode, ProductModelTypeEnum typeEnum, String identifier, List<SearchItem> condition, List<EsRange> rangeList);

}
