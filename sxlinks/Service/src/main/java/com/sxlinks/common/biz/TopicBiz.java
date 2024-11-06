package com.sxlinks.common.biz;

import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.enums.BCErrorEnum;
import com.sxlinks.common.enums.TopicTypeEnum;
import com.sxlinks.common.exception.BCGException;
import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.utils.StringUtils;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: TopicBiz.java, v 0.1 2020-12-09  Exp $$
  */
public class TopicBiz {
    /**从topic中解析物模型类型
     * 系统前缀/deviceCode/suffix
     * */
    public static String parseDeviceCode(String topic) {
        try {
            String[] splits = topic.split("/");
            return splits[2];
        } catch (Exception e) {
            throw BCGException.genException(BCErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**从topic中解析物模型类型
     * 系统前缀/funcType/identifier/deviceCode
     * */
    public static String parseFuncType(String topic) {
        try {
            String[] splits = topic.split("/");
            return splits[3].toUpperCase();
        } catch (Exception e) {
            throw BCGException.genException(BCErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**从topic中解析物模型类型
     * 系统前缀/funcType/identifier/deviceCode
     * */
    public static ProductModelTypeEnum parseFuncTypeEnum(String topic) {
        try {
            String[] splits = topic.split("/");
            return ProductModelTypeEnum.explain(splits[3].toUpperCase());
        } catch (Exception e) {
            throw BCGException.genException(BCErrorEnum.MQTT_TOPIC_INVALID, topic);
        }
    }
    /**
     * 根据协议编码组装订阅主题，用于内部MQTT客户端订阅
     * */
    public static String buildTopicByProduct(){
        return SXConstants.MQTT.GLOBAL_UP_PREFIX  + "/#";
    }

    public static String buildServiceInvoke(String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/service/invoke");
        return sb.toString();
    }

    public static String buildPropertySet( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/set");
        return sb.toString();
    }

    public static String buildPropertySetSub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/set/sub");
        return sb.toString();
    }
    /**设备属性读取*/
    public static String buildPropertyGet( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/get");
        return sb.toString();
    }
    /**设备属性读取*/
    public static String buildPropertyGetSub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/get/sub");
        return sb.toString();
    }
    /**
     * 设备属性上报
     * */
    public static String buildPropertyReport( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/prop");
        return sb.toString();
    }
    public static String buildPropertyReportSub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/prop/sub");
        return sb.toString();
    }

    public static String buildEventReport( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/event");
        return sb.toString();
    }
    public static String buildEventReportSub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/event/sub");
        return sb.toString();
    }

    public static String buildUpgradeSet( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/upgrade/set");
        return sb.toString();
    }
    public static String buildUpgradeSetSub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_DOWN_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/upgrade/set/sub");
        return sb.toString();
    }

    /**
     * 服务调用返回结果上报
     * */
    public static String buildServiceInvokeReply( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/message/reply");
        return sb.toString();
    }
    /**
     * 服务调用返回结果上报
     * */
    public static String buildServiceInvokeReplySub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/message/reply/sub");
        return sb.toString();
    }
    /**设备属性读取响应*/
    public static String buildPropertyGetReply( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/getreply");
        return sb.toString();
    }
    /**设备属性读取响应*/
    public static String buildPropertyGetReplySub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/property/getreply/sub");
        return sb.toString();
    }
    /**设备升级响应*/
    public static String buildUpgradeReply( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/upgrade/reply");
        return sb.toString();
    }
    /**设备升级响应*/
    public static String buildUpgradeReplySub( String deviceCode, String productCode){
        StringBuffer sb = new StringBuffer(SXConstants.MQTT.GLOBAL_UP_PREFIX)
                .append(productCode)
                .append("/")
                .append(deviceCode)
                .append("/upgrade/reply/sub");
        return sb.toString();
    }
    /**
     * 替换topic中的产品编码和设备编码,唯一作用是在系统收到来自网关设备上报子设备消息时将topic进行替换
     * @param orgTopic String 原始topic
     * @param productCode String 目标产品编码
     * @param deviceCode String 目标设备编码
     * @return  替换产品编码和设备编码后的新topic
     * */
    public static String topicFromGw2Sub(String orgTopic, String productCode, String deviceCode){
        if(StringUtils.isEmpty(orgTopic)){
            return orgTopic;
        }
        String[] splits = orgTopic.split("/");
        StringBuffer sb = new StringBuffer(splits[0])
                .append("/")
                .append(productCode)
                .append("/")
                .append(deviceCode);
        for(int index = 3; index < splits.length; index++){
            sb.append("/").append(splits[index]);
        }
        return sb.toString();
    }

    public static String buildTopicByType(String deviceCode, String productCode, TopicTypeEnum topicTypeEnum){
        switch (topicTypeEnum){
            case PROP_REPORT:
                return TopicBiz.buildPropertyReport(deviceCode, productCode);
            case EVENT_REPORT:
                return TopicBiz.buildEventReport(deviceCode, productCode);
            case SERVICE_INVOKE_REPLY:
                return TopicBiz.buildServiceInvokeReply(deviceCode,productCode);
            case PROP_READ_REPLY:
                return TopicBiz.buildPropertyGetReply(deviceCode, productCode);
            case FIRMWARE_UPGRADE_REPLY:
                return TopicBiz.buildUpgradeReply(deviceCode, productCode);
            case SERVICE_INVOKE:
                return TopicBiz.buildServiceInvoke(deviceCode, productCode);
            case PROP_SET:
                return TopicBiz.buildPropertySet(deviceCode, productCode);
            case PROP_READ:
                return TopicBiz.buildPropertyGet(deviceCode, productCode);
            case FIRMWARE_SET:
                return TopicBiz.buildUpgradeSet(deviceCode, productCode);
            default:
                throw new BCGException("topic类型错误");
        }
    }
    public static void  main(String[] args){
        String topic = "up/e4skexj7ktclxnpn/dev_868739053274977/prop";
        String pCode = "p1";
        String dCode = "d1";
        String newTopic = TopicBiz.topicFromGw2Sub(topic, pCode, dCode);
        System.out.println(newTopic);
    }
}
