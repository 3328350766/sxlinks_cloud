package com.sxlinks.modules.system.entity.productCenter.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
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
@Table(name="video_device_mobile_position")
public class VideoDeviceMobilePosition implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(type= IdType.AUTO) //采用数据库自增，非mybatis雪花巨长自增
	private Long id;
	/**
	 * 设备Id
	 */
	private String deviceId;

	/**
	 * 通道Id
	 */
	private String channelId;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 通知时间
	 */
	private String time;

	/**
	 * 经度
	 */
	private double longitude;

	/**
	 * 纬度
	 */
	private double latitude;

	/**
	 * 海拔高度
	 */
	private double altitude;

	/**
	 * 速度
	 */
	private double speed;

	/**
	 * 方向
	 */
	private double direction;

	/**
	 * 位置信息上报来源（Mobile Position、GPS Alarm）
	 */
	private String reportSource;

	/**
	 * 国内坐标系：经度坐标
	 */
	private double longitudeGcj02;

	/**
	 * 国内坐标系：纬度坐标
	 */
	private double latitudeGcj02;

	/**
	 * 国内坐标系：经度坐标
	 */
	private double longitudeWgs84;

	/**
	 * 国内坐标系：纬度坐标
	 */
	private double latitudeWgs84;
	
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
