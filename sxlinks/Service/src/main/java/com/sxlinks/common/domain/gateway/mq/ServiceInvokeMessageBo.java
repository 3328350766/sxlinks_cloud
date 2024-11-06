package com.sxlinks.common.domain.gateway.mq;//package com.sxlinks.common.domain.gateway.mq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sxlinks.common.metadata.ProductFuncTypeEnum;
//import lombok.Data;
//
//import java.util.Date;
//
///**
// * 存储在队列中的对象
//  * sxlinks.com.
//  * Copyright (c) 2020-2020 All Rights Reserved.
//  * 
//  * @author baba
//  * @version Id: QueueMsgBo.java, v 0.1 2020-12-09  Exp $$
//  */
//@Data
//public class ServiceInvokeMessageBo {
//    String     deviceCode;
//    /**下发服务标识符 */
//    String              identifier;
//    JSONObject command;
//    String              dataType;
//    String     productCode;
//    Date       currTime = new Date();
//   // IBaseProtocol baseProtocolService;
//    String topic;
//    ProductFuncTypeEnum funcType;
//    String protocolCode;
//    String messageId;
//
//}
