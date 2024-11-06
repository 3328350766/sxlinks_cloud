package com.sxlinks.common.enums;

import lombok.Getter;

/**
 * com.sxlinks.common.enums
 * project bytecub  bytecub.cn
 * 升级状态
 * @author baba 3328350766@qq.com
 * @date 2021/4/14
 */
@Getter
public enum  UpgradeEnum {
    WAITING(0, "等待","等待升级"),
    SEND(1, "已发送","已发送设备"),
    REPLY(2, "升级中","设备收到"),
    SUCCESS(3, "成功","升级成功"),
    FAILED(4, "失败","升级失败"),
    STOP(5, "停止","设备离线停止推送"),
    UNKNOWN(-1, "未知","未知错误码");
    Integer status;
    String smsg;
    String msg;

    UpgradeEnum(Integer status, String smsg, String msg) {
        this.status = status;
        this.msg = msg;
        this.smsg = smsg;
    }

    public static UpgradeEnum explain(Integer code){
        for (UpgradeEnum item: UpgradeEnum.values()){
            if(item.status.equals(code)){
                return item;
            }
        }

        return UNKNOWN;
    }


}
