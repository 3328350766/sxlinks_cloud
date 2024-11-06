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
@TableName("summary_electric_device_data_year")
public class SummaryElectricDeviceDataYear implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private String id;
	@ApiModelProperty(value = "设备id")
	private String deviceId;
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "分组id")
	private String divideId;
	@ApiModelProperty(value = "无功功耗")
	private Double wugong;
	@ApiModelProperty(value = "有功功耗")
	private Double yougong;
	@ApiModelProperty(value = "无功功耗起始")
	private Double wugongStart;
	@ApiModelProperty(value = "有功功耗起始")
	private Double yougongStart;
	@ApiModelProperty(value = "无功功耗结束")
	private Double wugongFinish;
	@ApiModelProperty(value = "有功功耗结束")
	private Double yougongFinish;

	//@ApiModelProperty(value = "归档日期")
	//private Date createDay; //归档日期

	@ApiModelProperty(value = "部门id")
	private Long deptId;

	@ApiModelProperty(value = "统计-年")
	private Integer summaryYear;

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