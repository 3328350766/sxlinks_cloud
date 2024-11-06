package com.sxlinks.modules.system.entity.productCenter.video;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@Table(name="video_media_server")
public class VideoMediaServer implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId //采用数据库自增，非mybatis雪花巨长自增
	private String id;

	@ApiModelProperty(value = "IP")
	private String ip;

	@ApiModelProperty(value = "hook使用的IP（zlm访问WVP使用的IP）")
	private String hookIp;

	@ApiModelProperty(value = "SDP IP")
	private String sdpIp;

	@ApiModelProperty(value = "流IP")
	private String streamIp;

	@ApiModelProperty(value = "HTTP端口")
	private int httpPort;

	@ApiModelProperty(value = "HTTPS端口")
	private int httpSSlPort;

	@ApiModelProperty(value = "RTMP端口")
	private int rtmpPort;

	@ApiModelProperty(value = "RTMPS端口")
	private int rtmpSSlPort;

	@ApiModelProperty(value = "RTP收流端口（单端口模式有用）")
	private int rtpProxyPort;

	@ApiModelProperty(value = "RTSP端口")
	private int rtspPort;

	@ApiModelProperty(value = "RTSPS端口")
	private int rtspSSLPort;

	@ApiModelProperty(value = "是否开启自动配置ZLM")
	private boolean autoConfig;

	@ApiModelProperty(value = "ZLM鉴权参数")
	private String secret;

	@ApiModelProperty(value = "keepalive hook触发间隔,单位秒")
	private int hookAliveInterval;

	@ApiModelProperty(value = "是否使用多端口模式")
	private boolean rtpEnable;

	@ApiModelProperty(value = "状态")
	private boolean status;

	@ApiModelProperty(value = "多端口RTP收流端口范围")
	private String rtpPortRange;

	@ApiModelProperty(value = "RTP发流端口范围")
	private String sendRtpPortRange;

	@ApiModelProperty(value = "assist服务端口")
	private int recordAssistPort;


	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	@ApiModelProperty(value = "上次心跳时间")
	private String lastKeepaliveTime;

	@ApiModelProperty(value = "是否是默认ZLM")
	private boolean defaultServer;

	
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
