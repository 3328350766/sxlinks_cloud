package com.sxlinks.modules.system.service.productCenter.impl.publish;

import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.domain.gateway.mq.MQSendMessageBo;
import com.sxlinks.plugin.redis.CacheTemplate;
import com.sxlinks.utils.SpringContextUtil;

/**
 *  * ByteCub.cn.
 *  * Copyright (c) 2020-2021 All Rights Reserved.
 *  * 
 *  * @author bytecub@163.com  songbin
 *  * @Date 2021/3/13  Exp $$
 *  
 */
public class ServiceInvokePublisher {
    private static CacheTemplate cacheTemplate = SpringContextUtil.getBean(CacheTemplate.class);

    static public void send(MQSendMessageBo bo){
        cacheTemplate.publish(SXConstants.REDIS_CHANNEL.SERVICE_INVOKE, bo);
    }
}
