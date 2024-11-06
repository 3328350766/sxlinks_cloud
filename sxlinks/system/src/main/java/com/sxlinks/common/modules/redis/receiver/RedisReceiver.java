package com.sxlinks.common.modules.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import com.sxlinks.common.base.BaseMap;
import com.sxlinks.common.constant.GlobalConstants;
import com.sxlinks.common.modules.redis.listener.JeecgRedisListerer;
import com.sxlinks.common.util.SpringContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        JeecgRedisListerer messageListener = SpringContextHolder.getHandler(handlerName.toString(), JeecgRedisListerer.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
