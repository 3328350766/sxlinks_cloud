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

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@Table(name="video_stream_proxy")
public class VideoStreamProxy implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "应用名")
	private String app;
	@ApiModelProperty(value = "流ID")
	private String stream;
	@ApiModelProperty(value = "流媒体服务ID")
	private String mediaServerId;
	@ApiModelProperty(value = "拉流地址")
	private String url;
	@ApiModelProperty(value = "拉流地址")
	private String src_url;
	@ApiModelProperty(value = "目标地址")
	private String dst_url;
	@ApiModelProperty(value = "超时时间")
	private int timeout_ms;
	@ApiModelProperty(value = "ffmpeg模板KEY")
	private String ffmpeg_cmd_key;
	@ApiModelProperty(value = "rtsp拉流时，拉流方式，0：tcp，1：udp，2：组播")
	private String rtp_type;
	@ApiModelProperty(value = "是否启用")
	private boolean enable;
	@ApiModelProperty(value = "是否启用HLS")
	private boolean enable_hls;
	@ApiModelProperty(value = "是否启用MP4")
	private boolean enable_mp4;
	@ApiModelProperty(value = "是否 无人观看时删除")
	private boolean enable_remove_none_reader;

	@ApiModelProperty(value = "是否 无人观看时自动停用")
	private boolean enable_disable_none_reader;
	@ApiModelProperty(value = "上级平台国标ID")
	private String platformGbId;
	
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
