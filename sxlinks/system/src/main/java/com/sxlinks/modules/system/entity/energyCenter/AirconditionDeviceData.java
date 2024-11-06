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
 * 空调设备表
 * <p>
 * 
 * @Author baba
 * @Since  2022-06-02
 */
@Data
@TableName("aircondition_device_data")
public class AirconditionDeviceData implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	@ApiModelProperty(value = "id")
	private String id;
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "分组id")
	private Long divideId;
	@ApiModelProperty(value = "分组编码")
	private String divideCode;
	@ApiModelProperty(value = "分组名称")
	private String divideName;
	@ApiModelProperty(value = "运行状态")
	private Integer runState;//运行状态(0-关机 1-打开)
	@ApiModelProperty(value = "模式")
	private Integer mode;//模式(自动-0 制冷-1 制热-2 除湿-3 送风-4  睡眠-5）
	@ApiModelProperty(value = "风速")
	private Integer windSpeed;//风速(自动-0 低速-1  中速-2 高速-3)
	@ApiModelProperty(value = "设置温度值")
	private Integer setupTemperature;
	@ApiModelProperty(value = "当前温度值")
	private Integer temperatureValue;
	@ApiModelProperty(value = "当前湿度值")
	private Integer humidnessValue;
	@ApiModelProperty(value = "红外人体感应")
	private Integer infraredHuman;//红外人体感应值(0-无人 1-有人)

	@ApiModelProperty(value = "部门id")
	private Long deptId;

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
