package com.sxlinks.modules.system.mapper.productCenter.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
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
public interface VideoDeviceMapper extends BaseMapper<VideoDevice> {


	@Select("SELECT " +
			"id, " +
			"deviceId, " +
			"coalesce(custom_name, name) as name, " +
			"password, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online" +
			" FROM video_device WHERE deviceId = #{deviceId}")
	VideoDevice getVideoDeviceBydeviceId(String deviceId);

	@Select("SELECT " +
			"id, " +
			"deviceId, " +
			"coalesce(custom_name, name) as name, " +
			"password, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online" +
			" FROM video_device WHERE deviceId = #{deviceId}")
	VideoDevice getDeviceByDeviceId(String deviceId);

	@Insert("INSERT INTO video_device (" +
			"deviceId, " +
			"name, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online" +
			") VALUES (" +
			"#{deviceId}," +
			"#{name}," +
			"#{manufacturer}," +
			"#{model}," +
			"#{firmware}," +
			"#{transport}," +
			"#{streamMode}," +
			"#{ip}," +
			"#{port}," +
			"#{hostAddress}," +
			"#{expires}," +
			"#{registerTime}," +
			"#{keepaliveTime}," +
			"#{createTime,jdbcType=DATE}," +
			"#{updateTime}," +
			"#{charset}," +
			"#{subscribeCycleForCatalog}," +
			"#{subscribeCycleForMobilePosition}," +
			"#{mobilePositionSubmissionInterval}," +
			"#{subscribeCycleForAlarm}," +
			"#{ssrcCheck}," +
			"#{geoCoordSys}," +
			"#{treeType}," +
			"#{online}" +
			")")
	int add(VideoDevice VideoDevice);

	@Update(value = {" <script>" +
			"UPDATE video_device " +
			"SET updateTime='${updateTime}'" +
			"<if test=\"deviceId != null\">, deviceId='${deviceId}'</if>" +
			"<if test=\"name != null\">, name='${name}'</if>" +
			"<if test=\"manufacturer != null\">, manufacturer='${manufacturer}'</if>" +
			"<if test=\"model != null\">, model='${model}'</if>" +
			"<if test=\"firmware != null\">, firmware='${firmware}'</if>" +
			"<if test=\"transport != null\">, transport='${transport}'</if>" +
			"<if test=\"ip != null\">, ip='${ip}'</if>" +
			"<if test=\"port != null\">, port=${port}</if>" +
			"<if test=\"hostAddress != null\">, hostAddress='${hostAddress}'</if>" +
			"<if test=\"online != null\">, online=${online}</if>" +
			"<if test=\"registerTime != null\">, registerTime='${registerTime}'</if>" +
			"<if test=\"keepaliveTime != null\">, keepaliveTime='${keepaliveTime}'</if>" +
			"<if test=\"expires != null\">, expires=${expires}</if>" +
			"WHERE id='${id}'"+
			" </script>"})
	int update(VideoDevice VideoDevice);

	@Select("SELECT " +
			"id, " +
			"deviceId, " +
			"coalesce(custom_name, name) as name, " +
			"password, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online," +
			"(SELECT count(0) FROM video_device_channel WHERE deviceId=de.deviceId) as channelCount FROM video_device de ORDER BY create_time DESC")
	IPage<VideoDevice> getVideoDevices(IPage<VideoDevice> iPage);

	@Delete("DELETE FROM video_device WHERE id=#{id}")
	int del(String id);

	@Update("UPDATE video_device SET online=0")
	int outlineForAll();

	@Select("SELECT " +
			"id, " +
			"deviceId, " +
			"coalesce(custom_name, name) as name, " +
			"password, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online " +
			" FROM video_device WHERE online = 1")
	List<VideoDevice> getOnlineVideoDevices();

	@Select("SELECT " +
			"id, " +
			"deviceId, " +
			"coalesce(custom_name, name) as name, " +
			"password, " +
			"manufacturer, " +
			"model, " +
			"firmware, " +
			"transport," +
			"streamMode," +
			"ip," +
			"port," +
			"hostAddress," +
			"expires," +
			"registerTime," +
			"keepaliveTime," +
			"create_time," +
			"updateTime," +
			"charset," +
			"subscribeCycleForCatalog," +
			"subscribeCycleForMobilePosition," +
			"mobilePositionSubmissionInterval," +
			"subscribeCycleForAlarm," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online" +
			" FROM video_device WHERE ip = #{host} AND port=${port}")
	VideoDevice getVideoDeviceByHostAndPort(String host, int port);

	@Update(value = {" <script>" +
			"UPDATE video_device " +
			"SET updateTime='${updateTime}'" +
			"<if test=\"id != null\">, ip='${id}'</if>" +
			"<if test=\"name != null\">, custom_name='${name}'</if>" +
			"<if test=\"password != null\">, password='${password}'</if>" +
			"<if test=\"streamMode != null\">, streamMode='${streamMode}'</if>" +
			"<if test=\"ip != null\">, ip='${ip}'</if>" +
			"<if test=\"port != null\">, port=${port}</if>" +
			"<if test=\"charset != null\">, charset='${charset}'</if>" +
			"<if test=\"subscribeCycleForCatalog != null\">, subscribeCycleForCatalog=${subscribeCycleForCatalog}</if>" +
			"<if test=\"subscribeCycleForMobilePosition != null\">, subscribeCycleForMobilePosition=${subscribeCycleForMobilePosition}</if>" +
			"<if test=\"mobilePositionSubmissionInterval != null\">, mobilePositionSubmissionInterval=${mobilePositionSubmissionInterval}</if>" +
			"<if test=\"subscribeCycleForAlarm != null\">, subscribeCycleForAlarm=${subscribeCycleForAlarm}</if>" +
			"<if test=\"ssrcCheck != null\">, ssrcCheck=${ssrcCheck}</if>" +
			"<if test=\"geoCoordSys != null\">, geoCoordSys=#{geoCoordSys}</if>" +
			"<if test=\"treeType != null\">, treeType=#{treeType}</if>" +
			"<if test=\"mediaServerId != null\">, mediaServerId=#{mediaServerId}</if>" +
			"WHERE id='${id}'"+
			" </script>"})
	int updateCustom(VideoDevice VideoDevice);

	@Insert("INSERT INTO video_device (" +
			"deviceId, " +
			"custom_name, " +
			"password, " +
			"create_time," +
			"updateTime," +
			"charset," +
			"ssrcCheck," +
			"geoCoordSys," +
			"treeType," +
			"online" +
			") VALUES (" +
			"#{deviceId}," +
			"#{name}," +
			"#{password}," +
			"#{createTime}," +
			"#{updateTime}," +
			"#{charset}," +
			"#{ssrcCheck}," +
			"#{geoCoordSys}," +
			"#{treeType}," +
			"#{online}" +
			")")
	void addCustomVideoDevice(VideoDevice device);
}
