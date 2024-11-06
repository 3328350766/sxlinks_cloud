package com.sxlinks.modules.system.service.productCenter.impl;

import com.alibaba.fastjson.JSONObject;
import com.sxlinks.common.domain.dto.request.device.InvokeReqDto;
import com.sxlinks.common.domain.gateway.mq.MQSendMessageBo;
import com.sxlinks.common.enums.BCErrorEnum;

import com.sxlinks.storage.IMessageReplyService;
import com.sxlinks.storage.entity.MessageReplyEntity;
import com.sxlinks.utils.IdGenerate;
import com.sxlinks.utils.JSONProvider;
import com.sxlinks.modules.system.service.productCenter.IServiceInvoke;
import com.sxlinks.modules.system.service.productCenter.impl.publish.ServiceInvokePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * com.sxlinks.gateway.direct.service.impl
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/18
 */
@Service
@Slf4j
public class ServiceInvokeImpl implements IServiceInvoke {
    @Autowired
    IMessageReplyService messageReplyService;

    @Override
    public Map<String, Object>  invokeWithReply(InvokeReqDto reqDto) {
        Map<String, Object> resultMap = new HashMap<>();
        String messageId = this.invokeNoReply(reqDto);
        MessageReplyEntity entity = this.loopQuery(reqDto, messageId);
        String body = entity.getBody();
        Map<String, Object> map = JSONProvider.parseObject(body, Map.class);
        if(CollectionUtils.isEmpty(map)){
            resultMap.put("messageId", entity.getMessageId());
            resultMap.put("code", BCErrorEnum.REPLY_TIMEOUT.getCode());
            resultMap.put("msg", BCErrorEnum.REPLY_TIMEOUT.getMsg());
            return resultMap;
        }
        resultMap = JSONProvider.parseJsonObject((JSONObject) map.get("result"), Map.class);
        resultMap.put("messageId", entity.getMessageId());
        return resultMap;
    }

    @Override
    public String invokeNoReply(InvokeReqDto reqDto) {
        log.info("下发指令请求...");
        MQSendMessageBo bo = new MQSendMessageBo();
        String messageId = IdGenerate.genId();
        bo.setIdentifier(reqDto.getIdentifier());
        bo.setMessageId(messageId);
        bo.setDeviceCode(reqDto.getDeviceCode());
        bo.setProductCode(reqDto.getProductCode());
        bo.setCommand(reqDto.getCommand());
        ServiceInvokePublisher.send(bo);
        return messageId;
    }

    private MessageReplyEntity loopQuery(InvokeReqDto reqDto, String messageId){
        MessageReplyEntity messageReplyEntity = null;
        long startTime = System.currentTimeMillis();
        if(null == reqDto.getTimeout()){
            reqDto.setTimeout(3);
        }
        while ((System.currentTimeMillis() - startTime) < reqDto.getTimeout() * 1000) {
            try{
                messageReplyEntity = messageReplyService.queryByMessageId(reqDto.getDeviceCode(), messageId);
                if (null != messageReplyEntity) {
                    break;
                }
                Thread.sleep(500);
            }catch (Exception e){
                log.warn("读取设备下发指定回执异常",e);
            }

        }
        if(null == messageReplyEntity){
            messageReplyEntity = new MessageReplyEntity();
            messageReplyEntity.setMessageId(messageId);
        }
        return messageReplyEntity;
    }
}
