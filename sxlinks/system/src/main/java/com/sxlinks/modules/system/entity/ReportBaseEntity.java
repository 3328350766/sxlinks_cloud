package com.sxlinks.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 地震项目表
 * <p>
 * 
 * @Author gaoliang
 * @Since  2022-05-18
 */
@Data
public class ReportBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(hidden = true,value = "可用状态 0-未删除 1-已删除")
	private Integer deleteFlag;

	/**创建人*/
	@ApiModelProperty(hidden = true)
	private String createBy;

	/**创建日期*/
	@ApiModelProperty(hidden = true)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**更新人*/
	@ApiModelProperty(hidden = true)
	private String modifyBy;

	/**更新日期*/
	@ApiModelProperty(hidden = true)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
}
