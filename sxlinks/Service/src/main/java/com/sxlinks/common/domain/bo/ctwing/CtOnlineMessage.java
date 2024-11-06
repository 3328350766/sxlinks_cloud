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
public class CtOnlineMessage {
    //	租户ID
    private String tenantId;
    //产品ID
    private String productId;
    //设备ID
    private String deviceId;
    //消息类型=deviceOnlineOfflineReport
    private String messageType;
    //上线：1，下线：0
    private Integer eventType;
    //	时间戳
    private Long timestamp;
}
