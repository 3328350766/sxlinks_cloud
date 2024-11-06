package com.sxlinks.modules.system.entity.productCenter;

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
 * @Since  2023-01-02
 */
@Data
@TableName("string_point")
public class StringPoint implements Serializable {

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
	@ApiModelProperty(value = "匹配方式(间隔分隔匹配-split 字符长度匹配-length)")
	private String matchMethod;
	@ApiModelProperty(value = "分隔字符串")
	private String splitString;
	@ApiModelProperty(value = "变量名")
	private String varName;
	@ApiModelProperty(value = "过滤字符串")
	private String replaceString;
	@ApiModelProperty(value = "起始位置")
	private Integer startPos;//当处理分隔模式下,此变量为间隔中的位置；当处于长度模式,此变量为起始位置
	@ApiModelProperty(value = "结束位置")
	private Integer finishPos;

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