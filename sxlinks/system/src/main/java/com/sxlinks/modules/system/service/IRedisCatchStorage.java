package com.sxlinks.modules.system.service;

import com.sxlinks.modules.system.zlm.dto.StreamInfo;

public interface IRedisCatchStorage {

    /**
     * 停止播放时删除
     *
     * @return
     */
    boolean stopPlay(StreamInfo streamInfo);

    StreamInfo queryPlayByDevice(String deviceId, String channelId);
}
