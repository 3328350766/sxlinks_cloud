package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataYear;
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
public interface SummaryEnergyDeviceDataYearMapper extends BaseMapper<SummaryEnergyDeviceDataYear> {


	@Select("select * from summary_energy_device_data_year where id=#{summary_energy_device_dataId}")
	public SysDepart getBySummaryEnergyDeviceDataId(@Param("summary_energy_device_dataId") String summary_energy_device_dataId);

	@Select("select * from summary_energy_device_data_year where code=#{code}")
	public SysDepart getBySummaryEnergyDeviceDataCode(@Param("summary_energy_device_dataCode") String code);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_energy_device_data_year where summary_year=#{year}")
	public Double getCountByPower(@Param("year") int year);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_energy_device_data_year ")
	public Double getCountByPowerAndTotal();

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_energy_device_data_year where summary_year=#{year} and create_by=#{userId}")
	public Double getCountByPowerAndUser(@Param("userId") String userId,@Param("year") int year);

	@Select("select cast(sum(power) AS decimal(15,2)) from summary_energy_device_data_year  where create_by=#{userId}")
	public Double getCountByPowerAndTotalAndUser(@Param("userId") String userId);

}
