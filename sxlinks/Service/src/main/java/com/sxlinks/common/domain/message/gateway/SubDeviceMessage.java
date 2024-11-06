package com.sxlinks.common.domain.message.gateway;

import lombok.Data;

/**
 * Created on 2021/8/10.
 * 网关设备上报子设备消息结构体
 * @author baba 3328350766@qq.com
 */
@Data
public class SubDeviceMessage {
    private String  deviceCode;
    private byte[]  message;
    private String  messageId;
}
