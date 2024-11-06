package com.sxlinks.modules.system.entity.productCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * <p>
 * 
 * @Author baba
 * @Since  2023-01-02
 */
@Data
@TableName("modbus_point")
public class ModbusPoint implements Serializable {

    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "描述")
	private String name;
	@ApiModelProperty(value = "点位id(属性id)")
	private Long pointId;
	@ApiModelProperty(value = "点位编码(属性编码)")
	private String pointCode;
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "设备从站地址")
	private String address;
	@ApiModelProperty(value = "功能码")
	private String code;
	@ApiModelProperty(value = "寄存器地址")
	private String startRegister;
	@ApiModelProperty(value = "起始寄存器类型(dec:十进制 hex:十六进制)")
	private String startRegisterType;
	private String startRegisterDec;
	@ApiModelProperty(value = "数据长度")
	private String dataLength;
	@ApiModelProperty(value = "数据类型")
	private String dataType;
	@ApiModelProperty(value = "字节顺序")
	private String byteSort;
	@ApiModelProperty(value = "网关id")
	private Long gatewayId;
	@ApiModelProperty(value = "网关编码")
	private String gatewayCode;
	@ApiModelProperty(value = "状态")
	private String state;

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
