package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sxlinks.modules.system.entity.productCenter.video.VideoMediaServer;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface VideoMediaServerMapper extends BaseMapper<VideoMediaServer> {

	@Insert("INSERT INTO video_media_server (" +
			"id, " +
			"ip, " +
			"hookIp, " +
			"sdpIp, " +
			"streamIp, " +
			"httpPort, " +
			"httpSSlPort, " +
			"rtmpPort, " +
			"rtmpSSlPort, " +
			"rtpProxyPort, " +
			"rtspPort, " +
			"rtspSSLPort, " +
			"autoConfig, " +
			"secret, " +
			"rtpEnable, " +
			"rtpPortRange, " +
			"sendRtpPortRange, " +
			"recordAssistPort, " +
			"defaultServer, " +
			"create_time, " +
			"updateTime, " +
			"hookAliveInterval" +
			") VALUES " +
			"(" +
			"'${id}', " +
			"'${ip}', " +
			"'${hookIp}', " +
			"'${sdpIp}', " +
			"'${streamIp}', " +
			"${httpPort}, " +
			"${httpSSlPort}, " +
			"${rtmpPort}, " +
			"${rtmpSSlPort}, " +
			"${rtpProxyPort}, " +
			"${rtspPort}, " +
			"${rtspSSLPort}, " +
			"${autoConfig}, " +
			"'${secret}', " +
			"${rtpEnable}, " +
			"'${rtpPortRange}', " +
			"'${sendRtpPortRange}', " +
			"${recordAssistPort}, " +
			"${defaultServer}, " +
			"#{createTime,jdbcType=DATE}, " +
			"'${updateTime}', " +
			"${hookAliveInterval})")
	int add(VideoMediaServer mediaServerItem);

	@Update(value = {" <script>" +
			"UPDATE video_media_server " +
			"SET updateTime='${updateTime}'" +
			"<if test=\"ip != null\">, ip='${ip}'</if>" +
			"<if test=\"hookIp != null\">, hookIp='${hookIp}'</if>" +
			"<if test=\"sdpIp != null\">, sdpIp='${sdpIp}'</if>" +
			"<if test=\"streamIp != null\">, streamIp='${streamIp}'</if>" +
			"<if test=\"httpPort != null\">, httpPort=${httpPort}</if>" +
			"<if test=\"httpSSlPort != null\">, httpSSlPort=${httpSSlPort}</if>" +
			"<if test=\"rtmpPort != null\">, rtmpPort=${rtmpPort}</if>" +
			"<if test=\"rtmpSSlPort != null\">, rtmpSSlPort=${rtmpSSlPort}</if>" +
			"<if test=\"rtpProxyPort != null\">, rtpProxyPort=${rtpProxyPort}</if>" +
			"<if test=\"rtspPort != null\">, rtspPort=${rtspPort}</if>" +
			"<if test=\"rtspSSLPort != null\">, rtspSSLPort=${rtspSSLPort}</if>" +
			"<if test=\"autoConfig != null\">, autoConfig=${autoConfig}</if>" +
			"<if test=\"rtpEnable != null\">, rtpEnable=${rtpEnable}</if>" +
			"<if test=\"rtpPortRange != null\">, rtpPortRange='${rtpPortRange}'</if>" +
			"<if test=\"sendRtpPortRange != null\">, sendRtpPortRange='${sendRtpPortRange}'</if>" +
			"<if test=\"secret != null\">, secret='${secret}'</if>" +
			"<if test=\"recordAssistPort != null\">, recordAssistPort=${recordAssistPort}</if>" +
			"<if test=\"hookAliveInterval != null\">, hookAliveInterval=${hookAliveInterval}</if>" +
			"WHERE id='${id}'"+
			" </script>"})
	int update(VideoMediaServer mediaServerItem);

	@Update(value = {" <script>" +
			"UPDATE video_media_server " +
			"SET updateTime='${updateTime}'" +
			"<if test=\"id != null\">, id='${id}'</if>" +
			"<if test=\"hookIp != null\">, hookIp='${hookIp}'</if>" +
			"<if test=\"sdpIp != null\">, sdpIp='${sdpIp}'</if>" +
			"<if test=\"streamIp != null\">, streamIp='${streamIp}'</if>" +
			"<if test=\"httpSSlPort != null\">, httpSSlPort=${httpSSlPort}</if>" +
			"<if test=\"rtmpPort != null\">, rtmpPort=${rtmpPort}</if>" +
			"<if test=\"rtmpSSlPort != null\">, rtmpSSlPort=${rtmpSSlPort}</if>" +
			"<if test=\"rtpProxyPort != null\">, rtpProxyPort=${rtpProxyPort}</if>" +
			"<if test=\"rtspPort != null\">, rtspPort=${rtspPort}</if>" +
			"<if test=\"rtspSSLPort != null\">, rtspSSLPort=${rtspSSLPort}</if>" +
			"<if test=\"autoConfig != null\">, autoConfig=${autoConfig}</if>" +
			"<if test=\"rtpEnable != null\">, rtpEnable=${rtpEnable}</if>" +
			"<if test=\"rtpPortRange != null\">, rtpPortRange='${rtpPortRange}'</if>" +
			"<if test=\"sendRtpPortRange != null\">, sendRtpPortRange='${sendRtpPortRange}'</if>" +
			"<if test=\"secret != null\">, secret='${secret}'</if>" +
			"<if test=\"recordAssistPort != null\">, recordAssistPort=${recordAssistPort}</if>" +
			"<if test=\"hookAliveInterval != null\">, hookAliveInterval=${hookAliveInterval}</if>" +
			"WHERE ip='${ip}' and httpPort=${httpPort}"+
			" </script>"})
	int updateByHostAndPort(VideoMediaServer mediaServerItem);

	@Select("SELECT * FROM video_media_server WHERE id='${id}'")
	VideoMediaServer queryOne(String id);

	@Select("SELECT * FROM video_media_server")
	List<VideoMediaServer> queryAll();

	@Select("SELECT * FROM video_media_server ORDER BY create_time DESC")
	IPage<VideoMediaServer> customPage(IPage<VideoMediaServer> iPage);

	@Delete("DELETE FROM video_media_server WHERE id='${id}'")
	void delOne(String id);

	@Select("DELETE FROM video_media_server WHERE ip='${host}' and httpPort=${port}")
	void delOneByIPAndPort(String host, int port);

	@Delete("DELETE FROM video_media_server WHERE defaultServer=1")
	int delDefault();

	@Select("SELECT * FROM video_media_server WHERE ip='${host}' and httpPort=${port}")
	VideoMediaServer queryOneByHostAndPort(String host, int port);

	@Select("SELECT * FROM video_media_server WHERE defaultServer=1")
	VideoMediaServer queryDefault();

	@Select("select * from video_media_server where id=#{video_media_serverId}")
	public VideoMediaServer getByVideoMediaServerId(@Param("video_media_serverId") String video_media_serverId);

	@Select("select * from video_media_server where video_media_server_code=#{code}")
	public VideoMediaServer getByVideoMediaServerCode(@Param("video_media_serverCode") String code);
}
