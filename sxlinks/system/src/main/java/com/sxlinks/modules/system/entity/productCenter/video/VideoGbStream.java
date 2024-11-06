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
@Table(name="video_gb_stream")
public class VideoGbStream implements Serializable {
    private static final long serialVersionUID = 1L;
//	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
//	private Long id;
	@TableId(type= IdType.AUTO)
	@ApiModelProperty(value = "ID")
	private Integer gbStreamId;
	@ApiModelProperty(value = "应用名")
	private String app;
	@ApiModelProperty(value = "流ID")
	private String stream;
	@ApiModelProperty(value = "国标ID")
	private String gbId;
	@ApiModelProperty(value = "名称")
	private String name;
	@ApiModelProperty(value = "流媒体ID")
	private String mediaServerId;
	@ApiModelProperty(value = "经度")
	private double longitude;
	@ApiModelProperty(value = "纬度")
	private double latitude;
	@ApiModelProperty(value = "流类型（拉流/推流）")
	private String streamType;
	@ApiModelProperty(value = "状态")
	private boolean status;
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
