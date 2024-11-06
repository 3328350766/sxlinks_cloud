package com.sxlinks.modules.system.entity.dataCenter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.sxlinks.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
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
@TableName("alarm_record")
public class AlarmRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**ID*/
	@TableId(type= IdType.AUTO) //实时告警采用手动输入
	private String id;
	@ApiModelProperty(value = "预警id")
	private Long alarmId;
	@ApiModelProperty(value = "预警名称")
	private String alarmName;
	@ApiModelProperty(value = "预警编码")
	private String alarmCode;
	@ApiModelProperty(value = "预警子顶id")
	private Long alarmItemId;
	@ApiModelProperty(value = "预警子顶名称")
	private String alarmItemName;
	@ApiModelProperty(value = "产品id")
	private Long productId;
	@ApiModelProperty(value = "产品编码")
	private String productCode;
	@ApiModelProperty(value = "产品名称")
	private String productName;
	@ApiModelProperty(value = "设备id")
	private Long deviceId;
	@ApiModelProperty(value = "设备编码")
	private String deviceCode;
	@ApiModelProperty(value = "设备名称")
	private String deviceName;
	@ApiModelProperty(value = "设备位置")
	private String deviceLocation;
	@ApiModelProperty(value = "分组id")
	private Long divideId;
	@ApiModelProperty(value = "属性id")
	private Long propertyId;
	@ApiModelProperty(value = "属性名称")
	private String propertyName;
	@ApiModelProperty(value = "当前值")
	private String value;
	@ApiModelProperty(value = "预警内容")
	private String content;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "处理状态")
	private String state;
	@ApiModelProperty(value = "时长(秒)")
	private Long duration; //时长(秒)
	@ApiModelProperty(value = "消警模式(0-手动 1-自动)")
	private String cancelMode;//消警模式(0-手动 1-自动)
	@ApiModelProperty(value = "消警状态(0-未消警 1-已消警)")
	private String cancelState;
	@ApiModelProperty(value = "消警时间")
	private Date cancelTime;

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
