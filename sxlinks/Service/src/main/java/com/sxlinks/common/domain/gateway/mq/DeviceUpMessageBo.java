package com.sxlinks.common.domain.gateway.mq;

import com.sxlinks.common.metadata.ProductModelTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 存储在队列中的对象
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: QueueMsgBo.java, v 0.1 2020-12-09  Exp $$
  */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpMessageBo {
    String        productCode;
    //IBaseProtocol baseProtocolService;
    byte[]        sourceMsg;
    String topic;
    /**mqtt消息中的packetId*/
    Long packetId;
    String deviceCode;
    Date currTime = new Date();
    /**物模型类型*/
    ProductModelTypeEnum funcType;
    //DeviceModelTypeEnum funcType;
}
