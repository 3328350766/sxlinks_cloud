package com.sxlinks.common.domain.message;

import lombok.Data;

/**
 * com.sxlinks.common.domain.message
 * project bytecub  bytecub.cn
 * 升级指令返回
 * @author baba 3328350766@qq.com
 * @date 2021/4/14
 */
@Data
public class UpgradeReplyMessage {
    String messageId;
    Integer code;
    String msg;
}
