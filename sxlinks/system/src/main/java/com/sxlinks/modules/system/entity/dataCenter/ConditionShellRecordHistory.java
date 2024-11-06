package com.sxlinks.modules.system.entity.dataCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.sxlinks.common.aspect.annotation.Dict;
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
@TableName("condition_shell_record_history")
public class ConditionShellRecordHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	@ApiModelProperty(value = "联动id")
	private Long conditionShellId;
	@ApiModelProperty(value = "联动子顶id")
	private Long conditionShellItemId;
	@ApiModelProperty(value = "productId")
	private String productId;
	@ApiModelProperty(value = "deviceId")
	private String deviceId;
	@ApiModelProperty(value = "propertyId")
	private String propertyId;
	@ApiModelProperty(value = "条件类型(property-属性 function-功能 event-事件)")
	private String type;    //告警条件类型(property-属性 function-功能 event-事件)
	@ApiModelProperty(value = "数据条件")
	private String dataCondition;    //数据条件
	@ApiModelProperty(value = "数据值")
	private String dataValue;    //数据值
	@ApiModelProperty(value = "预警名称")
	private String currentValue;
	@ApiModelProperty(value = "预警名称")
	private String content;

	@ApiModelProperty(value = "预警名称")
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
