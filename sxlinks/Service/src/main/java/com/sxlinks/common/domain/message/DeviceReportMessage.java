package com.sxlinks.common.domain.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sxlinks.common.domain.message.gateway.SubDeviceMessage;
import com.sxlinks.common.enums.DeviceReplyEnum;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  *  从设备上来的数据统一最重要格式化成这个样子
  * @author baba
  * @version Id: DeviceMessageRequest.java, v 0.1 2021-01-13  Exp $$
  */
@Data
public class DeviceReportMessage<T> {
    /**自定义协议不需要设置*/
    String deviceCode;
    /**自定义协议不需要设置*/
    String productCode;
    /**抵达服务器时候的服务器时间*/
    Date   timestamp;
    /**设备上传数据时的时间*/
    Date deviceTimestamp;
    /**设备上传的消息ID*/
    String messageId;
    /**设备主动数据上报消息体(属性/事件)
     * 设备属性读取的时候回执也必须是这种格式上报数据
     * Key   为物模型identifier
     * value 为物模型对应的数据
     * */
    Map<String, Object> value;
    /**消息回执消息体*/
    /**返回的消息内容*/
    String   replyMessage;
    /**设备处理的状态*/
    DeviceReplyEnum status;
    /**消息回执消息体 结束*/
    /**网关批量上报子设备状态*/
    List<String>    devices;
    /**网关上报子设备消息 starts*/

    /**子设备消息集合*/
    List<SubDeviceMessage> subDeviceMsgList;
    /**网关上报子设备消 ends*/

}
