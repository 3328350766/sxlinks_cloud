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
@TableName("summary_energy_device_data_day")
public class SummaryEnergyDeviceDataDay implements Serializable {
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
	@ApiModelProperty(value = "进水温度")
	private Double jinshui;
	@ApiModelProperty(value = "进水温度起始")
	private Double jinshuiStart;
	@ApiModelProperty(value = "进水温度结束")
	private Double jinshuiFinish;
	@ApiModelProperty(value = "回水温度")
	private Double huishui;
	@ApiModelProperty(value = "回水温度起始")
	private Double huishuiStart;
	@ApiModelProperty(value = "回水温度结束")
	private Double huishuiFinish;
	@ApiModelProperty(value = "热量")
	private Double reliang;
	@ApiModelProperty(value = "热量起始")
	private Double reliangStart;
	@ApiModelProperty(value = "热量结束")
	private Double reliangFinish;
	@ApiModelProperty(value = "冷量")
	private Double lengliang;
	@ApiModelProperty(value = "冷量起始")
	private Double lengliangStart;
	@ApiModelProperty(value = "冷量结束")
	private Double lengliangFinish;
	@ApiModelProperty(value = "流量")
	private Double liuliang;
	@ApiModelProperty(value = "流量起始")
	private Double liuliangStart;
	@ApiModelProperty(value = "流量结束")
	private Double liuliangFinish;
	@ApiModelProperty(value = "流速")
	private Double liushu;
	@ApiModelProperty(value = "流速起始")
	private Double liushuStart;
	@ApiModelProperty(value = "流速结束")
	private Double liushuFinish;
	@ApiModelProperty(value = "功能")
	private Double power;
	@ApiModelProperty(value = "功耗起始")
	private Double powerStart;
	@ApiModelProperty(value = "功耗结束")
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
