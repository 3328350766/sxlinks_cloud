package com.sxlinks.storage.impl;

import com.sxlinks.common.biz.EsUtil;
import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.domain.dto.PageResDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;
import com.sxlinks.common.domain.storage.EsMessage;
import com.sxlinks.common.enums.BCErrorEnum;
import com.sxlinks.common.enums.DeviceReplyEnum;
import com.sxlinks.common.exception.BCGException;
import com.sxlinks.common.metadata.BcMetaType;
import com.sxlinks.common.metadata.EsInsertDataBo;
import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.plugin.es.SampleEsClient;
import com.sxlinks.plugin.es.config.enums.BcEsMetaType;
import com.sxlinks.plugin.es.domain.EsRange;
import com.sxlinks.plugin.es.domain.PropertiesItem;
import com.sxlinks.plugin.es.domain.SearchItem;
import com.sxlinks.storage.IDataCenterService;
import com.sxlinks.storage.IMessageReplyService;
import com.sxlinks.storage.entity.MessageReplyEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 *  * sxlinks.com.  * Copyright (c) 2020-2020 All Rights Reserved.  *   * @author baba
 *  * @version Id: DataCenterServiceImpl.java, v 0.1 2020-12-11  Exp $$  
 */
@Slf4j
@Service
public class DataCenterServiceImpl implements IDataCenterService {
    @Autowired
    SampleEsClient esClient;
    @Autowired
    IMessageReplyService messageReplyService;

    @Override
    @Deprecated
    public Boolean createIndex(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier,
                               BcMetaType bcMetaType) {
        String aliases = EsUtil.buildIndexAliases(productCode, funcTypeEnum, identifier); //获取所有数据
        //String indexName = EsUtil.buildDevIndex(funcTypeEnum, productCode, identifier);
        Map<String, BcEsMetaType> sourceMap = new HashMap<>();
        sourceMap.put(identifier, bcMetaType.getEsMetaType());
        sourceMap.put("deviceCode", BcEsMetaType.KEYWORD);
        sourceMap.put("arrivedTime", BcEsMetaType.DATE);
        sourceMap.put("devTime", BcEsMetaType.DATE);
        return esClient.createIndex(aliases, sourceMap);
    }

    @Override
    public Boolean createIndexTemplate(String template, String indexAliases, String router, List<String> indexPatterns,
        Map<String, PropertiesItem> properties) {
        if (StringUtils.isEmpty(template) || CollectionUtils.isEmpty(indexPatterns)) {
            return false;
        }
        return esClient.createIndexTemplate(template, indexAliases, router, indexPatterns, properties);
    }

    @Override
    public Boolean saveData(EsInsertDataBo data) {
        EsMessage esMessage = data.getEsMessage();
        esMessage.setSaveTimestamp(new Date());
        Boolean ret = esClient.addDoc(data.getIndexName(), data.getEsMessage(), SXConstants.ES.HEADER_DEVICE);
        return ret;
    }

    @Override
    public Boolean batchSaveData(List<EsInsertDataBo> lsData){
        List lsNew=new ArrayList();
        for(int i=0;i<lsData.size();i++){
            EsInsertDataBo data=(EsInsertDataBo)lsData.get(i);
            EsMessage esMessage = data.getEsMessage();
            esMessage.setSaveTimestamp(new Date());
            data.setEsMessage(esMessage);
            lsNew.add(data);
        }

        Boolean ret = esClient.batchInsertRequest(lsNew, SXConstants.ES.HEADER_DEVICE);
        return ret;
    }

    @Override
    public Boolean deleteIndex(String sourceIndex) {
        return esClient.dropIndex(sourceIndex);
    }
    @Override
    public Boolean deleteIndex(String productCode, ProductModelTypeEnum funcTypeEnum, String identifier) {
        String aliases = EsUtil.buildIndexAliases(productCode, funcTypeEnum, identifier); //获取所有数据
        return esClient.dropIndex(aliases);
    }

    @Override
    public List<Map> testQueryDev(String index, String devCode) throws Exception {

        List<Map> result = esClient.queryByCondition(index, null, null, Map.class);
        return result;
    }

    @Override
    public long countDeviceRuntime(String productCode, String deviceCode, ProductModelTypeEnum typeEnum,
        String identifier, List<SearchItem> condition, List<EsRange> rangeList) {
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier); //获取所有数据
        //String aliases = EsUtil.buildDevIndex(typeEnum,productCode, identifier); //获取月份的
        return this.countDevice(aliases, condition, rangeList);
    }

    @Override
    public long countUpRange(String productCode, ProductModelTypeEnum typeEnum, String identifier, Date start,
                             Date end) {
        if(null == start || null == end){
            throw BCGException.genException(BCErrorEnum.INVALID_PARAM);
        }
        Date indexDate = new Date(start.getTime() + 100);
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier); //获取所有数据
        //String index = EsUtil.buildDevIndexByDate(indexDate, typeEnum, productCode , identifier);
        return this.countRange(aliases, start, end);
    }

    @Override
    public long countSetRange(String productCode, ProductModelTypeEnum typeEnum, String identifier, Date start, Date end) {
        if(null == start || null == end){
            throw BCGException.genException(BCErrorEnum.INVALID_PARAM);
        }
        Date indexDate = new Date(start.getTime() + 100);
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier); //获取所有数据
        //String index = EsUtil.buildDevPropertyIndexByDate(indexDate, typeEnum, productCode , identifier);
        return this.countRange(aliases, start, end);
    }

    private long countRange(String index, Date start, Date end) {
        List<EsRange> rangeList = new ArrayList<>();
        EsRange esRange = new EsRange();
        esRange.setMax(end);
        esRange.setField(SXConstants.ES.HEADER_TIMESTAMP);
        esRange.setMin(start);
        rangeList.add(esRange);
        return this.countDevice(index, null, rangeList);
    }

    /**
     * 获取所有属性的设置记录
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param conditionList
     * @param   startId int
     * @param size int
     * @param rangeList List
     * @return
     */
    @Override
    public List<DeviceRtHistoryResDto> searchDeviceRuntimeList(String productCode, String deviceCode, String identifier,
                                                               ProductModelTypeEnum typeEnum, List<SearchItem> conditionList, int startId, int size, List<EsRange> rangeList) {

        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier); //获取所有数据
        //String aliases = EsUtil.buildDevIndex(typeEnum,productCode, identifier); //获取月份的
        Boolean isQueryReply = false;
        if (ProductModelTypeEnum.SERVICE.equals(typeEnum)) {
            /** 服务调用记录需要查回执 */
            isQueryReply = true;
        }
        List<DeviceRtHistoryResDto> list =
            this.processHistoryItem(aliases, isQueryReply, deviceCode, conditionList, startId, size, rangeList);

        return list;
    }

    /**
     * 获取所有属性的设置记录
     * @param productCode String id
     * @param deviceCode String number
     * @param  identifier String
     * @param typeEnum ProductFuncTypeEnum context
     * @param condition List
     * @param   startId int
     * @param size int
     * @param rangeList List
     * @return
     */
    @Override
    public List<DeviceRtHistoryResDto> searchDeviceSetList(String productCode, String deviceCode, String identifier,
                                                           ProductModelTypeEnum typeEnum, List<SearchItem> condition, int startId, int size, List<EsRange> rangeList) {
        String aliases = EsUtil.buildPropSetIndexAliases(productCode, typeEnum, identifier); //获取所有数据
        //String aliases = EsUtil.buildDevIndex(typeEnum,productCode, identifier); //获取月份的-去除月份
        List<DeviceRtHistoryResDto> list =
            this.processHistoryItem(aliases, true, deviceCode, condition, startId, size, rangeList);
        return list;
    }

    /** 将设备下行数据根据messageId组装回列表中 */
    private List<DeviceRtHistoryResDto> processHistoryItem(String aliases, Boolean isQueryReply, String deviceCode,
        List<SearchItem> condition, int startId, int size, List<EsRange> rangeList) {
        List<DeviceRtHistoryResDto> list =
            this.searchDeviceHistory(aliases, condition, startId, size, rangeList, DeviceRtHistoryResDto.class);
        if (!isQueryReply) {
            /** 不需要查回执 */
            return list;
        }
        List<String> ids = new ArrayList<>();
        for (DeviceRtHistoryResDto message : list) {
            ids.add(message.getMessageId());
        }
        List<MessageReplyEntity> replies = messageReplyService.searchHistory(deviceCode, ids);
        Map<String, MessageReplyEntity> map = new HashMap<>();
        for (MessageReplyEntity entity : replies) {
            if (!map.containsKey(entity.getMessageId())) {
                map.put(entity.getMessageId(), entity);
            } else {
                MessageReplyEntity oldEntity = map.get(entity.getMessageId());
                if (oldEntity.getTimestamp().before(entity.getTimestamp())) {
                    // 老的数据时间小于当前数据时间，这个时候当前数据就要覆盖老的数据
                    map.put(entity.getMessageId(), entity);
                }
            }
        }

        for (DeviceRtHistoryResDto resDto : list) {
            MessageReplyEntity rtMessage = map.get(resDto.getMessageId());
            if (null == rtMessage) {
                resDto.setReplyMessage("");
                resDto.setStatus(DeviceReplyEnum.NORELY.getCode());
                resDto.setReplyTime(null);
            } else {
                resDto.setReplyMessage(rtMessage.getBody());
                resDto.setStatus(rtMessage.getStatus());
                resDto.setReplyTime(rtMessage.getTimestamp());
            }
        }
        return list;
    }

    @Override
    public long countDeviceSet(String productCode, String deviceCode, ProductModelTypeEnum typeEnum, String identifier,
                               List<SearchItem> condition, List<EsRange> rangeList) {
        String aliases = EsUtil.buildPropSetIndexAliases(productCode, typeEnum, identifier);
        return this.countDevice(aliases, condition, rangeList);
    }

    @Override
    public <T> PageResDto<T> searchByPage(String productCode, String deviceCode, String identifier,
                                          ProductModelTypeEnum typeEnum, List<SearchItem> conditionList, Class<T> resClass, int startId, int size,
                                          List<EsRange> rangeList) {
        String aliases = EsUtil.buildIndexAliases(productCode, typeEnum, identifier);
        String[] indices = new String[1];
        indices[0] = aliases;
        List<T> list = new ArrayList<>();
        try {
            list = esClient.searchByPage(indices, SXConstants.ES.HEADER_DEVICE, conditionList, resClass, startId, size,
                SXConstants.ES.HEADER_TIMESTAMP, SortOrder.DESC, rangeList);
            long total = esClient.count(indices, SXConstants.ES.HEADER_DEVICE, conditionList, rangeList);
            int pageNo = startId / size;
            return PageResDto.genResult(pageNo, size, total, list, null);

        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return PageResDto.genResult(1, size, 0, list, null);
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new BCGException(BCErrorEnum.INNER_EXCEPTION.getCode(), BCErrorEnum.INNER_EXCEPTION.getMsg(),
                "查询ES异常", e);
        }
    }

    /**
     * 根据索引查询历史数据总数
     *
     */
    private long countDevice(String index, List<SearchItem> condition, List<EsRange> rangeList) {
        String[] indices = new String[1];
        indices[0] = index;
        try {
            long total = esClient.count(indices, null, condition, rangeList);
            return total;
        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return 0;
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 根据索引查询历史数据
     *
     */
    private <T> List<T> searchDeviceHistory(String index, List<SearchItem> conditionList, int startId, int size,
        List<EsRange> rangeList, Class<T> tClass) {
        String[] indices = new String[1];
        List<T> list = new ArrayList<>();
        indices[0] = index;
        try {
            list = esClient.searchByPage(indices, SXConstants.ES.HEADER_DEVICE, conditionList, tClass, startId, size,
                SXConstants.ES.HEADER_TIMESTAMP, SortOrder.DESC, rangeList);
            return list;

        } catch (ElasticsearchStatusException ese) {
            if (RestStatus.NOT_FOUND.equals(ese.status())) {
                /** 索引不存在 */
                return list;
            }
            throw new RuntimeException(ese);
        } catch (Exception e) {
            log.warn("", e);
            throw new BCGException(BCErrorEnum.INNER_EXCEPTION.getCode(), BCErrorEnum.INNER_EXCEPTION.getMsg(),
                "查询ES异常", e);
        }
    }
}
