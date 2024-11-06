package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryWaterDeviceDataHour;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 *
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface SummaryWaterDeviceDataHourMapper extends BaseMapper<SummaryWaterDeviceDataHour> {


	@Select("select * from summary_water_device_data_hour where id=#{summary_water_device_dataId}")
	public SummaryWaterDeviceDataHour getBySummaryWaterDeviceDataId(@Param("summary_water_device_dataId") String summary_water_device_dataId);

	@Select("select * from summary_water_device_data_hour where code=#{code}")
	public SummaryWaterDeviceDataHour getBySummaryWaterDeviceDataCode(@Param("summary_water_device_dataCode") String code);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_water_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour}")
	public Double getCountByPower(@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_water_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day}")
	public Double getCountByPowerAndDay(@Param("year") int year,@Param("month") int month,@Param("day") int day);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_water_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour} and create_by=#{userId}")
	public Double getCountByPowerAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_water_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and create_by=#{userId}")
	public Double getCountByPowerAndDayAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day);

}
