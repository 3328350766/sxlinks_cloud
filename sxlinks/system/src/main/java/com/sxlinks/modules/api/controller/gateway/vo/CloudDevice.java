package com.sxlinks.modules.api.controller.gateway.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author gaoliang
 * @Since  2022-01-22
 */
@Data

public class CloudDevice implements Serializable {
    private static final long serialVersionUID = 1L;

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
	private Date updateTime;
	@ApiModelProperty(value = "设备密钥")
	String deviceSecret;//设备密钥
	@ApiModelProperty(value = "固件版本")
	String firmwareVersion;//固件版本 无需输入
	@ApiModelProperty(value = "最近一次上线主机地址")
	String devHost;//最近一次上线主机地址
	@ApiModelProperty(value = "最近一次上线端口")
	Integer devPort;//最近一次上线端口

	//属性列表
	@ApiModelProperty(value = "属性列表")
	List lsProperty;
	//功能列表
	@ApiModelProperty(value = "功能列表")
	List lsFunction;
	//属性列表-modbus点位列表
	@ApiModelProperty(value = "属性modbus点位列表")
	List lsPropertyModbusPoint;
	//属性列表-string点位列表
	@ApiModelProperty(value = "属性string点位列表")
	List lsPropertyStringPoint;
	//属性列表-json点位列表
	@ApiModelProperty(value = "属性json点位列表")
	List lsPropertyJsonPoint;
	//属性列表
	@ApiModelProperty(value = "属性自定义点位列表")
	List lsPropertyCustomPoint;
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
