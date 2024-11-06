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
@TableName("summary_gas_device_data_hour")
public class SummaryGasDeviceDataHour implements Serializable {
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
	@ApiModelProperty(value = "功率")
	private Double power;

	@ApiModelProperty(value = "起始功耗")
	private Double powerStart;
	@ApiModelProperty(value = "结束功耗")
	private Double powerFinish;

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
	@ApiModelProperty(value = "统计-小时")
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
