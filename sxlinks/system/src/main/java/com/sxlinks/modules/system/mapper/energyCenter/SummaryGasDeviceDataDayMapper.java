package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataDay;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataHour;
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
public interface SummaryGasDeviceDataDayMapper extends BaseMapper<SummaryGasDeviceDataDay> {


	@Select("select * from summary_gas_device_data where id=#{summary_gas_device_dataId}")
	public SysDepart getBySummaryWaterDeviceDataId(@Param("summary_gas_device_dataId") String summary_gas_device_dataId);

	@Select("select * from summary_gas_device_data where code=#{code}")
	public SysDepart getBySummaryWaterDeviceDataCode(@Param("summary_gas_device_dataCode") String code);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_gas_device_data_day where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day}")
	public Double getCountByPower(@Param("year") int year,@Param("month") int month,@Param("day") int day);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_gas_device_data_day where summary_year=#{year} and  summary_month=#{month}")
	public Double getCountByPowerAndMonth(@Param("year") int year,@Param("month") int month);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_gas_device_data_day where summary_year=#{year} and  summary_month=#{month} and summary_day=#{day} and create_by=#{userId}")
	public Double getCountByPowerAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month,@Param("day") int day);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_gas_device_data_day where summary_year=#{year} and  summary_month=#{month} and create_by=#{userId}")
	public Double getCountByPowerAndMonthAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month);

}
