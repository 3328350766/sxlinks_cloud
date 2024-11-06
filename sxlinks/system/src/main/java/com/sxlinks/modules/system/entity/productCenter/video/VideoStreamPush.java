package com.sxlinks.modules.system.entity.productCenter.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@Table(name="video_stream_push")
public class VideoStreamPush implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	/**
	 * 应用名
	 */
	@ApiModelProperty(value = "应用名")
	private String app;

	/**
	 * 流id
	 */
	@ApiModelProperty(value = "流id")
	private String stream;

	/**
	 * 观看总人数，包括hls/rtsp/rtmp/http-flv/ws-flv
	 */
	@ApiModelProperty(value = "观看总人数")
	private String totalReaderCount;



	/**
	 * 产生源类型，
	 * unknown = 0,
	 * rtmp_push=1,
	 * rtsp_push=2,
	 * rtp_push=3,
	 * pull=4,
	 * ffmpeg_pull=5,
	 * mp4_vod=6,
	 * device_chn=7
	 */
	@ApiModelProperty(value = "产生源类型")
	private int originType;


	/**
	 * 产生源类型的字符串描述
	 */
	@ApiModelProperty(value = "产生源类型的字符串描述")
	private String originTypeStr;

	/**
	 * 产生源的url
	 */
	@ApiModelProperty(value = "产生源的url")
	private String originUrl;

	/**
	 * 存活时间，单位秒
	 */
	@ApiModelProperty(value = "存活时间，单位秒")
	private Long aliveSecond;

	/**
	 * 音视频轨道
	 */
	@ApiModelProperty(value = "音视频轨道")
	private String vhost;

	/**
	 * 使用的流媒体ID
	 */
	@ApiModelProperty(value = "使用的流媒体ID")
	private String mediaServerId;

	/**
	 * 使用的服务ID
	 */
	@ApiModelProperty(value = "使用的服务ID")
	private String serverId;

	/**
	 * 推流时间
	 */
	@ApiModelProperty(value = "推流时间")
	private String pushTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	/**
	 * 是否正在推流
	 */
	@ApiModelProperty(value = "是否正在推流")
	private boolean pushIng;

	/**
	 * 是否自己平台的推流
	 */
	@ApiModelProperty(value = "是否自己平台的推流")
	private boolean self;

	
	/**创建人*/
	private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	private String modifyBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
}
