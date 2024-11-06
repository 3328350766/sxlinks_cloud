package com.sxlinks.common.domain.message;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 服务端发送到设备的下行数据格式
  * @author baba
  * @version Id: DeviceDownMessage.java, v 0.1 2021-01-20  Exp $$
  */
@Data
public class DeviceDownMessage {
    String messageId;
    /**时间戳，单位毫秒*/
    Long timestamp;
    /**消息体*/
    Object body;
    /**下发的指令,服务调用的时候就是服务标识符*/
    String identifier;
    String productCode;
    /**
     * 目标设备编码，如果是发完网关子设备的指令，这里指的是子设备的设备编码
     * */
    String deviceCode;
    /**网关设备编码
     * subFlag 为true时该值才有意义
     * */
    String gwDeviceCode;
    /**
     * true: 表示是一条发往网关子设备的指令
     * 默认是false
     * */
    Boolean subFlag = false;
}
