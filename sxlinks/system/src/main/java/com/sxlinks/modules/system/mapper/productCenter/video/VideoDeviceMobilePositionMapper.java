package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceMobilePosition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface VideoDeviceMobilePositionMapper extends BaseMapper<VideoDeviceMobilePosition> {


	@Select("select * from video_device_mobile_position where id=#{video_device_mobile_positionId}")
	public VideoDeviceMobilePosition getByVideoDeviceMobilePositionId(@Param("video_device_mobile_positionId") String video_device_mobile_positionId);

	@Select("select * from video_device_mobile_position where video_device_mobile_position_code=#{code}")
	public VideoDeviceMobilePosition getByVideoDeviceMobilePositionCode(@Param("video_device_mobile_positionCode") String code);

}
