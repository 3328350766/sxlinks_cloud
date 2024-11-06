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
@Table(name="video_device_channel")
public class VideoDeviceChannel implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**
	 * 通道国标编号
	 */
	@ApiModelProperty(value = "通道国标编号")
	private String channelId;

	/**
	 * 设备国标编号
	 */
	@ApiModelProperty(value = "设备国标编号")
	private String deviceId;

	/**
	 * 通道名
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 生产厂商
	 */
	@ApiModelProperty(value = "生产厂商")
	private String manufacture;

	/**
	 * 型号
	 */
	@ApiModelProperty(value = "型号")
	private String model;

	/**
	 * 设备归属
	 */
	@ApiModelProperty(value = "设备归属")
	private String owner;

	/**
	 * 行政区域
	 */
	@ApiModelProperty(value = "行政区域")
	private String civilCode;

	/**
	 * 警区
	 */
	@ApiModelProperty(value = "警区")
	private String block;

	/**
	 * 安装地址
	 */
	@ApiModelProperty(value = "安装地址")
	private String address;

	/**
	 * 是否有子设备 1有, 0没有
	 */
	@ApiModelProperty(value = "是否有子设备 1有, 0没有")
	private int parental;

	/**
	 * 父级id
	 */
	@ApiModelProperty(value = "父级id")
	private String parentId;

	/**
	 * 信令安全模式  缺省为0; 0:不采用; 2: S/MIME签名方式; 3: S/ MIME加密签名同时采用方式; 4:数字摘要方式
	 */
	@ApiModelProperty(value = "信令安全模式  缺省为0; 0:不采用; 2: S/MIME签名方式; 3: S/ MIME加密签名同时采用方式; 4:数字摘要方式")
	private int safetyWay;

	/**
	 * 注册方式 缺省为1;1:符合IETFRFC3261标准的认证注册模 式; 2:基于口令的双向认证注册模式; 3:基于数字证书的双向认证注册模式
	 */
	@ApiModelProperty(value = "注册方式 缺省为1;1:符合IETFRFC3261标准的认证注册模 式; 2:基于口令的双向认证注册模式; 3:基于数字证书的双向认证注册模式")
	private int registerWay;

	/**
	 * 证书序列号
	 */
	@ApiModelProperty(value = "证书序列号")
	private String certNum;

	/**
	 * 证书有效标识 缺省为0;证书有效标识:0:无效1: 有效
	 */
	@ApiModelProperty(value = "证书有效标识 缺省为0;证书有效标识:0:无效1: 有效")
	private int certifiable;

	/**
	 * 证书无效原因码
	 */
	@ApiModelProperty(value = "证书无效原因码")
	private int errCode;

	/**
	 * 证书终止有效期
	 */
	@ApiModelProperty(value = "证书终止有效期")
	private String endTime;

	/**
	 * 保密属性 缺省为0; 0:不涉密, 1:涉密
	 */
	@ApiModelProperty(value = "保密属性 缺省为0; 0:不涉密, 1:涉密")
	private String secrecy;

	/**
	 * IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	private String ipAddress;

	/**
	 * 端口号
	 */
	@ApiModelProperty(value = "端口号")
	private int port;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 云台类型
	 */
	@ApiModelProperty(value = "云台类型")
	private int PTZType;

	/**
	 * 云台类型描述字符串
	 */
	@ApiModelProperty(value = "云台类型描述字符串")
	private String PTZTypeText;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	/**
	 * 在线/离线
	 * 1在线,0离线
	 * 默认在线
	 * 信令:
	 * <Status>ON</Status>
	 * <Status>OFF</Status>
	 * 遇到过NVR下的IPC下发信令可以推流， 但是 Status 响应 OFF
	 */
	@ApiModelProperty(value = "在线/离线， 1在线,0离线")
	private int status;

	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private double longitude;

	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	private double latitude;

	/**
	 * 经度 GCJ02
	 */
	@ApiModelProperty(value = "GCJ02坐标系经度")
	private double longitudeGcj02;

	/**
	 * 纬度 GCJ02
	 */
	@ApiModelProperty(value = "GCJ02坐标系纬度")
	private double latitudeGcj02;

	/**
	 * 经度 WGS84
	 */
	@ApiModelProperty(value = "WGS84坐标系经度")
	private double longitudeWgs84;

	/**
	 * 纬度 WGS84
	 */
	@ApiModelProperty(value = "WGS84坐标系纬度")
	private double latitudeWgs84;

	/**
	 * 子设备数
	 */
	@ApiModelProperty(value = "子设备数")
	private int subCount;

	/**
	 * 流唯一编号，存在表示正在直播
	 */
	@ApiModelProperty(value = "流唯一编号，存在表示正在直播")
	private String  streamId;

	/**
	 *  是否含有音频
	 */
	@ApiModelProperty(value = "是否含有音频")
	private boolean hasAudio;

	/**
	 * 标记通道的类型，0->国标通道 1->直播流通道 2->业务分组/虚拟组织/行政区划
	 */
	@ApiModelProperty(value = "标记通道的类型，0->国标通道 1->直播流通道 2->业务分组/虚拟组织/行政区划")
	private int channelType;

	/**
	 * 业务分组
	 */
	@ApiModelProperty(value = "业务分组")
	private String businessGroupId;

	/**
	 * GPS的更新时间
	 */
	@ApiModelProperty(value = "GPS的更新时间")
	private String gpsTime;
	
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
