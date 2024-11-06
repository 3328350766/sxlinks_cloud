package com.sxlinks.common.modules.redis.listener;

import com.sxlinks.common.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface JeecgRedisListerer {

    void onMessage(BaseMap message);

}
