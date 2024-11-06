package com.sxlinks.common.domain.bo.ctwing;

import lombok.Data;

/**
 *  * sxlinks.com.
 *  * Copyright (c) 2020-2021 All Rights Reserved.
 *  * 
 *  * @author baba
 *  * @Date 2021/4/6  Exp $$
 *  
 */
@Data
public class CtReportMessage {
    //	租户ID
    private String tenantId;
    //产品ID
    private String productId;
    //设备ID
    private String deviceId;
    //消息类型=dataReport
    private String messageType;
    //NB终端设备识别号
    private String IMEI;
    //NB终端sim卡标识
    private String IMSI;
    //设备标识
    private String deviceType;
    //数据上报主题
    private String topic;
    //合作伙伴ID
    private String assocAssetId;
    //时间戳
    private Long timestamp;
    //上行报文序号
    private Integer upPacketSN;
    //数据上报报文序号
    private Integer upDataSN;
    //服务ID
    private String serviceId;
    //协议类型
    private String protocol;
    //消息负载，非透传消息格式为payload:消息内容JSON；透传消息格式为payload:{"APPdata":"消息内容BASE64编码"}
    private String payload;
}
