package com.sxlinks.modules.system.entity.visual;

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
 * 组态表
 * <p>
 * 
 * @Author baba
 * @Since  2023-2-26
 */
@Data
@TableName("configure")
public class Configure implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(value = "名称")
	private String name;    //名称
	@ApiModelProperty(value = "编码")
	private String code;    //编码

	@ApiModelProperty(value = "图片url路径")
	private String imgUrl;
	@ApiModelProperty(value = "图片相对路径")
	private String imgPath;    //原设备编码
	@ApiModelProperty(value = "描述")
	private String description;    //描述

	@ApiModelProperty(value = "状态")
	private String state;    //状态
	@ApiModelProperty(value = "删除标志")
	private Integer delFlag;    //删除标志

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
