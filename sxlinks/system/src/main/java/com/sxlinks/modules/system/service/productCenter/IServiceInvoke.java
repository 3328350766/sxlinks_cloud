package com.sxlinks.modules.system.service.productCenter;

import com.sxlinks.common.domain.dto.request.device.InvokeReqDto;

import java.util.Map;

/**
 * com.sxlinks.gateway.direct.service
 * project bytecub  bytecub.cn
 * 设备指令下发
 * @author baba 3328350766@qq.com
 * @date 2021/4/18
 */
public interface IServiceInvoke {
    /**调用服务后，会等待设备响应*/
    Map<String, Object> invokeWithReply(InvokeReqDto reqDto);
    /**调用服务后，不等待设备响应
     * @return messageId
     * */
    String invokeNoReply(InvokeReqDto reqDto);
}
