package com.sxlinks.modules.system.entity.energyCenter;

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
@TableName("summary_area_energy_data_hour")
public class SummaryAreaEnergyDataHour implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private String id;
	@ApiModelProperty(value = "分组id")
	private String divideId;
	@ApiModelProperty(value = "分组名称")
	private String divideName;
	@ApiModelProperty(value = "分组编码")
	private String divideCode;

	@ApiModelProperty(value = "用电无功功耗")
	private Double electricWugong;
	@ApiModelProperty(value = "用电有功功耗")
	private Double electricYougong;
	@ApiModelProperty(value = "用电无功功耗起始")
	private Double electricWugongStart;
	@ApiModelProperty(value = "用电有功功耗起始")
	private Double electricYougongStart;
	@ApiModelProperty(value = "用电无功功耗结束")
	private Double electricWugongFinish;
	@ApiModelProperty(value = "用电有功功耗结束")
	private Double electricYougongFinish;

	@ApiModelProperty(value = "用水")
	private Double water;
	@ApiModelProperty(value = "用水起始")
	private Double waterStart;
	@ApiModelProperty(value = "用水结束")
	private Double waterFinish;

	@ApiModelProperty(value = "用气")
	private Double gas;
	@ApiModelProperty(value = "用气起始")
	private Double gasStart;
	@ApiModelProperty(value = "用气结束")
	private Double gasFinish;

	@ApiModelProperty(value = "冷量")
	private Double lengliang;
	@ApiModelProperty(value = "冷量起始")
	private Double lengliangStart;
	@ApiModelProperty(value = "冷量结束")
	private Double lengliangFinish;

	@ApiModelProperty(value = "热量")
	private Double reliang;
	@ApiModelProperty(value = "热量起始")
	private Double reliangStart;
	@ApiModelProperty(value = "热量结束")
	private Double reliangFinish;

	//@ApiModelProperty(value = "归档日期")
	//private Date createDay; //归档日期

	@ApiModelProperty(value = "部门id")
	private Long deptId;

	@ApiModelProperty(value = "统计-年")
	private Integer summaryYear;
	@ApiModelProperty(value = "统计-月")
	private Integer summaryMonth;
	@ApiModelProperty(value = "统计-日")
	private Integer summaryDay;
	@ApiModelProperty(value = "统计-时")
	private Integer summaryHour;
	
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
