package com.sxlinks.modules.system.service.impl;

import com.sxlinks.modules.system.mapper.productCenter.video.VideoDeviceChannelMapper;
import com.sxlinks.modules.system.service.IVideoManagerStorage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 视频设备数据存储-jdbc实现
 * swwheihei
 * 2020年5月6日 下午2:31:42
 */
@SuppressWarnings("rawtypes")
@Component
public class VideoManagerStorageImpl implements IVideoManagerStorage {

	@Resource
	private VideoDeviceChannelMapper deviceChannelMapper;

	@Override
	public void stopPlay(String deviceId, String channelId) {
		deviceChannelMapper.stopPlay(deviceId, channelId);
	}
}
