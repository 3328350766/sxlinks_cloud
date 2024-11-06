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
@TableName("belong_type_item")
public class BelongTypeItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(value = "名称")
	private String name;    //名称
	@ApiModelProperty(value = "编码")
	private String code;    //编码
	@ApiModelProperty(value = "归类id")
	private Long belongId;    //归类id
	@ApiModelProperty(value = "归类编码")
	private String belongCode;    //归类编码
	@ApiModelProperty(value = "表名")
	private String tableName;    //表名
	@ApiModelProperty(value = "字段")
	private String tableField;    //字段
	@ApiModelProperty(value = "计算类型(increment-递增 realtime-实时)")
	private String computeType;    //计算类型(increment-递增 realtime-实时)
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
