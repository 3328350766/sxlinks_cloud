package com.sxlinks.modules.system.service.impl;

import com.sxlinks.modules.system.service.IRedisCatchStorage;
import com.sxlinks.modules.system.zlm.dto.StreamInfo;
import com.sxlinks.modules.system.zlm.UserSetting;
import com.sxlinks.modules.system.zlm.VideoManagerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("rawtypes")
@Component
public class RedisCatchStorageImpl implements IRedisCatchStorage {

    private final Logger logger = LoggerFactory.getLogger(RedisCatchStorageImpl.class);

    @Autowired
    private UserSetting userSetting;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean stopPlay(StreamInfo streamInfo) {
        if (streamInfo == null) {
            return false;
        }
        return delRedis(String.format("%S_%s_%s_%s_%s_%s", VideoManagerConstants.PLAYER_PREFIX,
                userSetting.getServerId(),
                streamInfo.getMediaServerId(),
                streamInfo.getStream(),
                streamInfo.getDeviceID(),
                streamInfo.getChannelId()));
    }

    @Override
    public StreamInfo queryPlayByDevice(String deviceId, String channelId) {
        List<Object> playLeys = scan(String.format("%S_%s_*_*_%s_%s", VideoManagerConstants.PLAYER_PREFIX,
                userSetting.getServerId(),
                deviceId,
                channelId));
        if (playLeys == null || playLeys.size() == 0) {
            return null;
        }
        return (StreamInfo)getRedisValue(playLeys.get(0).toString());
    }

    private Object getRedisValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    private Boolean delRedis(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
        return true;
    }

    /**
     * 模糊查询
     * @param query 查询参数
     * @return
     */
    public List<Object> scan(String query) {
        Set<String> resultKeys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            ScanOptions scanOptions = ScanOptions.scanOptions().match("*" + query + "*").count(1000).build();
            Cursor<byte[]> scan = connection.scan(scanOptions);
            Set<String> keys = new HashSet<>();
            while (scan.hasNext()) {
                byte[] next = scan.next();
                keys.add(new String(next));
            }
            return keys;
        });

        return new ArrayList<>(resultKeys);
    }
}
