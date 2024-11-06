package com.sxlinks.modules.system.entity.ruleEngine;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("device_rule_item")
public class DeviceRuleItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;


	@ApiModelProperty(value = "规则id")
	private Long ruleId;
	@ApiModelProperty(value = "规则编码")
	private String ruleCode;
	@ApiModelProperty(value = "产品id")
	private Long productId;
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(value = "属性id")
	private Long propertyId;
	@ApiModelProperty(value = "条件类型(property-属性 function-功能 event-事件)")
	private String type;    //告警条件类型(property-属性 function-功能 event-事件)
	@ApiModelProperty(value = "数据条件(等于:等于 大于:大于 小于:小于 大于等于:大于等于 小于等于:小于等于)")
	private String dataCondition;    //数据条件(等于:等于 大于:大于 小于:小于 大于等于:大于等于 小于等于:小于等于)
	@ApiModelProperty(value = "数据值")
	private String dataValue;    //数据值

	@ApiModelProperty(value = "状态")
	private String state;    //状态

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