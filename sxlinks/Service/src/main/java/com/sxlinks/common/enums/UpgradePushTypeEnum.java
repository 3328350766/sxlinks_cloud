package com.sxlinks.common.enums;

import lombok.Getter;

/**
 * com.sxlinks.common.enums
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/14
 */
@Getter
public enum UpgradePushTypeEnum {
    URL(1, "URL升级"),
    FILE(2, "文件升级"),
    MIXED(3, "混合升级");
    Integer type;
    String msg;

    UpgradePushTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
