package com.sxlinks.modules.system.entity.productCenter.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 国标设备第一层设备列表
 * <p>
 * 
 * @Author baba
 * @Since  2023-1-11
 */
@Data
@TableName("video_device")
@ApiModel(value = "国标设备")
public class VideoDevice implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	/**
	 * 设备国标编号
	*/
	@TableField(value = "deviceId")
	@ApiModelProperty(value = "设备国标编号")
	private String deviceId;

	/**
	 * 设备名
	*/
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 生产厂商
	*/
	@TableField(value = "manufacturer")
	@ApiModelProperty(value = "生产厂商")
	private String manufacturer;

	/**
	 * 型号
	*/
	@ApiModelProperty(value = "型号")
	private String model;

	/**
	 * 固件版本
	*/
	@ApiModelProperty(value = "固件版本")
	private String firmware;

	/**
	 * 传输协议
	 * UDP/TCP
	*/
	@ApiModelProperty(value = "传输协议（UDP/TCP）")
	private String transport;

	/**
	 * 数据流传输模式
	 * UDP:udp传输
	 * TCP-ACTIVE：tcp主动模式
	 * TCP-PASSIVE：tcp被动模式
	*/
	@TableField(value = "streamMode")
	@ApiModelProperty(value = "数据流传输模式")
	private String streamMode;

	/**
	 * wan地址_ip
	*/
	@ApiModelProperty(value = "IP")
	private String  ip;

	/**
	 * wan地址_port
	*/
	@ApiModelProperty(value = "端口")
	private int port;

	/**
	 * wan地址
	*/
	@TableField(value = "hostAddress")
	@ApiModelProperty(value = "wan地址")
	private String  hostAddress;

	/**
	 * 在线
	*/
	@TableField(value = "online")
	@ApiModelProperty(value = "是否在线，1为在线，0为离线")
	private int online;


	/**
	 * 注册时间
	*/
	@TableField(value = "registerTime")
	@ApiModelProperty(value = "注册时间")
	private String registerTime;


	/**
	 * 心跳时间
	*/
	@TableField(value = "keepaliveTime")
	@ApiModelProperty(value = "心跳时间")
	private String keepaliveTime;

	/**
	 * 通道个数
	*/
	@TableField(value = "channelCount")
	@ApiModelProperty(value = "通道个数")
	private int channelCount;

	/**
	 * 注册有效期
	*/
	@ApiModelProperty(value = "注册有效期")
	private int expires;


	/**
	 * 更新时间
	*/
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	/**
	 * 设备使用的媒体id, 默认为null
	*/
	@TableField(value = "mediaServerId")
	@ApiModelProperty(value = "设备使用的媒体id, 默认为null")
	private String mediaServerId;

	/**
	 * 字符集, 支持 UTF-8 与 GB2312
	*/
	@ApiModelProperty(value = "符集, 支持 UTF-8 与 GB2312")
	private String charset ;

	/**
	 * 目录订阅周期，0为不订阅
	*/
	@TableField(value = "subscribeCycleForCatalog")
	@ApiModelProperty(value = "目录订阅周期，0为不订阅")
	private int subscribeCycleForCatalog;

	/**
	 * 移动设备位置订阅周期，0为不订阅
	*/
	@TableField(value = "subscribeCycleForMobilePosition")
	@ApiModelProperty(value = "移动设备位置订阅周期，0为不订阅")
	private int subscribeCycleForMobilePosition;

	/**
	 * 移动设备位置信息上报时间间隔,单位:秒,默认值5
	*/
	@TableField(value = "mobilePositionSubmissionInterval")
	@ApiModelProperty(value = "移动设备位置信息上报时间间隔,单位:秒,默认值5")
	private int mobilePositionSubmissionInterval = 5;

	/**
	 * 报警订阅周期，0为不订阅
	*/
	@TableField(value = "subscribeCycleForAlarm")
	@ApiModelProperty(value = "报警心跳时间订阅周期，0为不订阅")
	private int subscribeCycleForAlarm;

	/**
	 * 是否开启ssrc校验，默认关闭，开启可以防止串流
	*/
	@TableField(value = "ssrcCheck")
	@ApiModelProperty(value = "是否开启ssrc校验，默认关闭，开启可以防止串流")
	private boolean ssrcCheck = true;

	/**
	 * 地理坐标系， 目前支持 WGS84,GCJ02
	*/
	@TableField(value = "geoCoordSys")
	@ApiModelProperty(value = "地理坐标系， 目前支持 WGS84,GCJ02")
	private String geoCoordSys;

	/**
	 * 树类型 国标规定了两种树的展现方式 行政区划：CivilCode 和业务分组:BusinessGroup
	*/
	@TableField(value = "treeType")
	@ApiModelProperty(value = "树类型 国标规定了两种树的展现方式 行政区划：CivilCode 和业务分组:BusinessGroup")
	private String treeType;

	@ApiModelProperty(value = "密码")
	private String password;

	
	/**创建人 */
	@TableField(value = "")
	private String createBy;
	/**创建日期 */
	@TableField(value = "")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人 */
	@TableField(value = "")
	private String modifyBy;
	/**更新日期 */
	@TableField(value = "")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
}
