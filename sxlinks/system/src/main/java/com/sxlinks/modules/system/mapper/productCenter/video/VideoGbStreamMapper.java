package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxlinks.modules.system.entity.productCenter.video.VideoGbStream;
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
public interface VideoGbStreamMapper extends BaseMapper<VideoGbStream> {


	@Select("select * from video_gb_stream where id=#{video_gb_streamId}")
	public VideoGbStream getByVideoGbStreamId(@Param("video_gb_streamId") String video_gb_streamId);

	@Select("select * from video_gb_stream where video_gb_stream_code=#{code}")
	public VideoGbStream getByVideoGbStreamCode(@Param("video_gb_streamCode") String code);

}
