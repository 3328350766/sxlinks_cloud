package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxlinks.modules.system.entity.productCenter.video.VideoStreamPush;
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
public interface VideoStreamPushMapper extends BaseMapper<VideoStreamPush> {


	@Select("select * from video_stream_push where id=#{video_stream_pushId}")
	public VideoStreamPush getByVideoStreamPushId(@Param("video_stream_pushId") String video_stream_pushId);

	@Select("select * from video_stream_push where video_stream_push_code=#{code}")
	public VideoStreamPush getByVideoStreamPushCode(@Param("video_stream_pushCode") String code);

}
