package com.sxlinks.modules.system.entity.report;

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
@TableName("report_dashboard")
public class ReportDashboard implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(value = "报表编码")
	private String reportCode;

	@ApiModelProperty(value = "看板标题")
	private String title;

	@ApiModelProperty(value = "宽度px")
	private Long width;

	@ApiModelProperty(value = "高度px")
	private Long height;

	@ApiModelProperty(value = "背景色")
	private String backgroundColor;

	@ApiModelProperty(value = "背景图片")
	private String backgroundImage;

	@ApiModelProperty(value = "工作台中的辅助线")
	private String presetLine;

	@ApiModelProperty(value = "自动刷新间隔秒，数据字典REFRESH_TYPE")
	private Integer refreshSeconds;

	@ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
	private Integer enableFlag;

	@ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
	private Integer deleteFlag;

	@ApiModelProperty(value = "排序，降序")
	private Integer sort;

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

	private Integer version;
}
