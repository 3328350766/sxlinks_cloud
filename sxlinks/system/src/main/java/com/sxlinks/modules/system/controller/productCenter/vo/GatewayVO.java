package com.sxlinks.modules.system.controller.productCenter.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class GatewayVO implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;//设备编码
	@ApiModelProperty(value = "设备名称")
	private String deviceName;//设备名称
	@ApiModelProperty(value = "网关设备编码")
	private String gwDevCode;//网关设备编码 如果是子设备，关联的网关设备编码
    @ApiModelProperty(value = "协议编码")
    private String protocolCode;//关联的消息协议编码
    @ApiModelProperty(value = "协议类型")
    private String protocolType;//关联的消息协议编码
    @ApiModelProperty(value = "节点类型")
    private String nodeType;//节点类型 直连设备DIRECT/子设备SUB/网关设备GATEWAY
    @ApiModelProperty(value = "网络类型")
    private String networkType; //网络类型(wifi-wifi无线,2/3/4/5G-蜂窝,NB-Iot:窄带,Rj-45:以太网)
	@ApiModelProperty(value = "产品编码")
	private String productCode;//产品编码
	@ApiModelProperty(value = "删除标志")
	private Integer delFlag;//删除标志 0:未删除 1 删除
	@ApiModelProperty(value = "启用状态")
	private Integer enableStatus;//启用状态 0:未启用 1:启用
	@ApiModelProperty(value = "在线状态")
	private Integer activeStatus;//在线状态 0:离线 1:在线
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
	private Date lastOnlineTime;//最近一次上线时间
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
	private Date createTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
	private Date updateTime;
	@ApiModelProperty(value = "设备密钥")
	String deviceSecret;//设备密钥
	@ApiModelProperty(value = "固件版本")
	String firmwareVersion;//固件版本 无需输入
	@ApiModelProperty(value = "最近一次上线主机地址")
	String devHost;//最近一次上线主机地址
	@ApiModelProperty(value = "最近一次上线端口")
	Integer devPort;//最近一次上线端口

	@ApiModelProperty(value = "网关类型(hardware-硬网关 software-软网关)")
	String gatewayType;//网关类型
	@ApiModelProperty(value = "坐标类型(wgs84-标准gps gcj-02-火星坐标系 bd-09-百度坐标系 other-其它类型)")
	String locationType;//坐标系类型
	@ApiModelProperty(value = "经度")
	String lng;//经度
	@ApiModelProperty(value = "纬度")
	String lat;//纬度
	@ApiModelProperty(value = "项目id")
	Long projectId;//项目id
	@ApiModelProperty(value = "项目编码")
	String projectCode;//项目编码

	@ApiModelProperty(value = "网关子设备列表")
	List lsSubDevice; //网关子设备列表
}
