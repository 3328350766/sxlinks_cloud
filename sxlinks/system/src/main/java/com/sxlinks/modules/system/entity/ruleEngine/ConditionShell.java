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
 * 条件联动表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@TableName("condition_shell")
public class ConditionShell implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**机构/部门名称*/
	@ApiModelProperty(value = "名称")
	private String name;    //名称
	@ApiModelProperty(value = "编码")
	private String code;    //编码
	@ApiModelProperty(value = "原设备id")
	private Long sourceDeviceId; //原设备id
	@ApiModelProperty(value = "原设备编码")
	private String sourceDeviceCode;    //原设备编码
	@TableField(exist = false)
	private String sourceDeviceName; //原设备名称
	@ApiModelProperty(value = "名称")
	private String description;    //名称
	@ApiModelProperty(value = "联动类型")
	private String type;    //联动类型

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
