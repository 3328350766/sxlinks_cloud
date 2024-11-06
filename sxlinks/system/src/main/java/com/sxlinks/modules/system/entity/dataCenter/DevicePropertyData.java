package com.sxlinks.modules.system.entity.dataCenter;

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
 * @Since  2022-06-02
 */
@Data
@TableName("device_property_data")
public class DevicePropertyData implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id; //属性id

	@Excel(name="名称",width=256)
	private String name; //属性名称

	@Excel(name="编码",width=64)
	private String code;

	private String deviceId;
	private String deviceCode;
	private String deviceName;
	private String productId;
	private String productCode;
	private String productName;
	private String dataLength;
	private String dataType;
	private String byteSort;
	private String gatewayId; //网关id
	private String gatewayCode; //网关编码
	private String gatewayName; //网关名称
	private String value;

	/**缩写*/
	@Excel(name="状态",width=64)
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
