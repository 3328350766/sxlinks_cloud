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
@TableName("report_dashboard_widget")
public class ReportDashboardWidget implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	@ApiModelProperty(value = "报表编码")
	private String reportCode;

	@ApiModelProperty(value = "组件类型参考字典DASHBOARD_PANEL_TYPE")
	private String type;

	@ApiModelProperty(value = "组件的渲染属性json")
	private String setup;

	@ApiModelProperty(value = "组件的数据属性json")
	private String data;

	@ApiModelProperty(value = "组件的配置属性json")
	private String collapse;

	@ApiModelProperty(value = "组件的大小位置属性json")
	private String position;

	private String options;

	@ApiModelProperty(value = "自动刷新间隔秒")
	private Integer refreshSeconds;

	@ApiModelProperty(value = "0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG")
	private Integer enableFlag;

	@ApiModelProperty(value = " 0--未删除 1--已删除 DIC_NAME=DEL_FLAG")
	private Integer deleteFlag;

	@ApiModelProperty(value = "排序，图层的概念")
	private Long sort;


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
