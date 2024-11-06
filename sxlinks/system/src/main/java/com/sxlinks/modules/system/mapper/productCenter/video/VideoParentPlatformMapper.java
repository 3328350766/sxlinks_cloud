package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sxlinks.modules.system.entity.productCenter.video.VideoParentPlatform;
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
public interface VideoParentPlatformMapper extends BaseMapper<VideoParentPlatform> {


	@Select("select * from video_parent_platform where id=#{video_parent_platformId}")
	public VideoParentPlatform getByVideoParentPlatformId(@Param("video_parent_platformId") String video_parent_platformId);

	@Select("select * from video_parent_platform where video_parent_platform_code=#{code}")
	public VideoParentPlatform getByVideoParentPlatformCode(@Param("video_parent_platformCode") String code);

}
