package com.sxlinks.modules.system.entity.ruleEngine;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@TableName("condition_shell_item")
public class ConditionShellItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**机构/部门名称*/
	@ApiModelProperty(value = "名称")
	private String name;    //名称
	@ApiModelProperty(value = "联动id")
	private Long conditionShellId;//联动id
	@ApiModelProperty(value = "产品id")
	private Long productId;//产品id
	@ApiModelProperty(value = "产品编码")
	private String productCode;//属性id
	@ApiModelProperty(value = "产品名称")
	private String productName;//属性id
	@ApiModelProperty(value = "设备id")
	private Long deviceId;//设备id
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;//属性id
	@ApiModelProperty(value = "设备名称")
	private String deviceName;//属性id
	@ApiModelProperty(value = "属性id")
	private Long propertyId;//属性id
	@ApiModelProperty(value = "属性编码")
	private String propertyCode;//属性id
	@ApiModelProperty(value = "属性名称")
	private String propertyName;//属性id
	@ApiModelProperty(value = "目标设备id")
	private Long targetDeviceId;//目标设备id
	@ApiModelProperty(value = "目标设备编码")
	private String targetDeviceCode;//目标设备编码
	@ApiModelProperty(value = "目标设备名称")
	private String targetDeviceName;//目标设备名称
	@ApiModelProperty(value = "目标功能id")
	private Long targetFunctionId;  //目标功能id
	@ApiModelProperty(value = "目标功能code")
	private Long targetFunctionCode;  //目标功能编码
	@TableField(exist = false)
	@ApiModelProperty(value = "目标功能名称")
	private String targetFunctionName;
	@ApiModelProperty(value = "目标属性id")
	private Long targetPropertyId;  //目标属性id
	@ApiModelProperty(value = "目标属性编码")
	private String targetPropertyCode;  //目标属性编码
	@ApiModelProperty(value = "目标属性名称")
	private String targetPropertyName;//目标属性名称
	@ApiModelProperty(value = "条件类型")
	private String type;    //条件类型(property-属性 function-功能 event-事件)
	@ApiModelProperty(value = "数据条件")
	private String dataCondition;    //数据条件-数据条件(等于:等于 大于:大于 小于:小于 大于等于:大于等于 小于等于:小于等于)
	@ApiModelProperty(value = "数据值")
	private String dataValue;    //数据值
	@ApiModelProperty(value = "目标属性指定数据值")
	private String targetDataValue;    //数据值
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
