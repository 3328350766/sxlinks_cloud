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
@TableName("report_share")
public class ReportShare implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;

	/** 分享编码，系统生成，默认UUID */
	private String shareCode;

	/** 分享有效期类型，DIC_NAME=SHARE_VAILD */
	private Integer shareValidType;

	/** 分享有效期 */
	private Date shareValidTime;

	/** 分享token */
	private String shareToken;

	/** 分享url */
	private String shareUrl;

	/** 报表编码 */
	private String reportCode;

	/** 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG */
	private Integer enableFlag;

	/** 0--未删除 1--已删除 DIC_NAME=DELETE_FLAG */
	private Integer deleteFlag;

	/** 分享码 */
	private String sharePassword;

	@TableField(exist = false)
	private boolean sharePasswordFlag;

	/** 大屏类型 report excel */
	@TableField(exist = false)
	private String reportType;

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
