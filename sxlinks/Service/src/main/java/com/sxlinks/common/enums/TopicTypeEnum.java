package com.sxlinks.common.enums;

import lombok.Getter;

/**
 * Created on 2021/8/24.
 * 主题类型
 * @author baba 3328350766@qq.com
 */
@Getter
public enum TopicTypeEnum {
    PROP_REPORT(1, "属性上报"),
    EVENT_REPORT(2, "事件上报"),
    SERVICE_INVOKE_REPLY(3, "服务调用返回结果上报"),
    PROP_READ_REPLY(4, "设备属性读取响应"),
    FIRMWARE_UPGRADE_REPLY(5, "设备升级状态响应"),
    SERVICE_INVOKE(6, "调用设备服务"),
    PROP_SET(7, "设备属性设置"),
    PROP_READ(8, "设备属性读取"),
    FIRMWARE_SET(9, "下发升级指令");
    Integer code;
    String msg;

    TopicTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
