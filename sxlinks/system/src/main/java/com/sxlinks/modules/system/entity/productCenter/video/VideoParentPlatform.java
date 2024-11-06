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
@Table(name="video_parent_platform")
public class VideoParentPlatform implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**
	 * 是否启用
	 */
	@ApiModelProperty(value = "是否启用")
	private boolean enable;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * SIP服务国标编码
	 */
	@ApiModelProperty(value = "SIP服务国标编码")
	private String serverGBId;

	/**
	 * SIP服务国标域
	 */
	@ApiModelProperty(value = "SIP服务国标域")
	private String serverGBDomain;

	/**
	 * SIP服务IP
	 */
	@ApiModelProperty(value = "SIP服务IP")
	private String serverIP;

	/**
	 * SIP服务端口
	 */
	@ApiModelProperty(value = "SIP服务端口")
	private int serverPort;

	/**
	 * 设备国标编号
	 */
	@ApiModelProperty(value = "设备国标编号")
	private String deviceGBId;

	/**
	 * 设备ip
	 */
	@ApiModelProperty(value = "设备ip")
	private String deviceIp;

	/**
	 * 设备端口
	 */
	@ApiModelProperty(value = "设备端口")
	private String devicePort;

	/**
	 * SIP认证用户名(默认使用设备国标编号)
	 */
	@ApiModelProperty(value = "SIP认证用户名(默认使用设备国标编号)")
	private String username;

	/**
	 * SIP认证密码
	 */
	@ApiModelProperty(value = "SIP认证密码")
	private String password;

	/**
	 * 注册周期 (秒)
	 */
	@ApiModelProperty(value = "注册周期 (秒)")
	private int expires;

	/**
	 * 心跳周期(秒)
	 */
	@ApiModelProperty(value = "心跳周期(秒)")
	private int keepTimeout;

	/**
	 * 传输协议
	 * UDP/TCP
	 */
	@ApiModelProperty(value = "传输协议")
	private String transport;

	/**
	 * 字符集
	 */
	@ApiModelProperty(value = "字符集")
	private String characterSet;

	/**
	 * 允许云台控制
	 */
	@ApiModelProperty(value = "允许云台控制")
	private boolean ptz;

	/**
	 * RTCP流保活
	 */
	@ApiModelProperty(value = "RTCP流保活")
	private boolean rtcp;

	/**
	 * 在线状态
	 */
	@ApiModelProperty(value = "在线状态")
	private boolean status;

	/**
	 * 在线状态
	 */
	@ApiModelProperty(value = "在线状态")
	private int channelCount;

	/**
	 * 默认目录Id,自动添加的通道多放在这个目录下
	 */
	@ApiModelProperty(value = "默认目录Id,自动添加的通道多放在这个目录下")
	private String catalogId;

	/**
	 * 已被订阅目录信息
	 */
	@ApiModelProperty(value = "已被订阅目录信息")
	private boolean catalogSubscribe;

	/**
	 * 已被订阅报警信息
	 */
	@ApiModelProperty(value = "已被订阅报警信息")
	private boolean alarmSubscribe;

	/**
	 * 已被订阅移动位置信息
	 */
	@ApiModelProperty(value = "已被订阅移动位置信息")
	private boolean mobilePositionSubscribe;

	/**
	 * 点播未推流的设备时是否使用redis通知拉起
	 */
	@ApiModelProperty(value = "点播未推流的设备时是否使用redis通知拉起")
	private boolean startOfflinePush;

	/**
	 * 目录分组-每次向上级发送通道信息时单个包携带的通道数量，取值1,2,4,8
	 */
	@ApiModelProperty(value = "目录分组-每次向上级发送通道信息时单个包携带的通道数量，取值1,2,4,8")
	private int catalogGroup;

	/**
	 * 行政区划
	 */
	@ApiModelProperty(value = "行政区划")
	private String administrativeDivision;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private String updateTime;


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
