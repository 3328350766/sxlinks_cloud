package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataHour;
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
public interface SummaryElectricDeviceDataHourMapper extends BaseMapper<SummaryElectricDeviceDataHour> {


	@Select("select * from summary_electric_device_data where id=#{summary_electric_device_dataId}")
	public SummaryElectricDeviceDataHour getBySummaryElectricDeviceDataId(@Param("summary_electric_device_dataId") String summary_electric_device_dataId);

	@Select("select * from summary_electric_device_data where code=#{code}")
	public SummaryElectricDeviceDataHour getBySummaryElectricDeviceDataCode(@Param("summary_electric_device_dataCode") String code);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour}")
	public Double getCountByYougong(@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour}")
	public Double getCountByWugong(@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day}")
	public Double getCountByYougongAndDay(@Param("year") int year,@Param("month") int month,@Param("day") int day);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day}")
	public Double getCountByWugongAndDay(@Param("year") int year,@Param("month") int month,@Param("day") int day);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour} and create_by=#{userId}")
	public Double getCountByYougongAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and summary_hour=#{hour} and create_by=#{userId}")
	public Double getCountByWugongAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("hour") int hour);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and create_by=#{userId}")
	public Double getCountByYougongAndDayAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_hour where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and create_by=#{userId}")
	public Double getCountByWugongAndDayAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day);

}
