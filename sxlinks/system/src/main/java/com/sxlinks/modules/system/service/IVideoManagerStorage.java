package com.sxlinks.modules.system.service;

/**    
 * @description:视频设备数据存储接口
 * @author: swwheihei
 * @date:   2020年5月6日 下午2:14:31     
 */
@SuppressWarnings("rawtypes")
public interface IVideoManagerStorage {

	/**
	 * 停止播放
	 * @param deviceId 设备id
	 * @param channelId 通道ID
	 */
	public void stopPlay(String deviceId, String channelId);
}
