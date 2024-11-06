package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxlinks.modules.system.entity.productCenter.video.VideoStreamProxy;
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
public interface VideoStreamProxyMapper extends BaseMapper<VideoStreamProxy> {


	@Select("select * from video_stream_proxy where id=#{video_stream_proxyId}")
	public VideoStreamProxy getByVideoStreamProxyId(@Param("video_stream_proxyId") String video_stream_proxyId);

	@Select("select * from video_stream_proxy where video_stream_proxy_code=#{code}")
	public VideoStreamProxy getByVideoStreamProxyCode(@Param("video_stream_proxyCode") String code);

}
