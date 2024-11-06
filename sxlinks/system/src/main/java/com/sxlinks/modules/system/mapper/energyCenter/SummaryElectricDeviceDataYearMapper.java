package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataYear;
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
public interface SummaryElectricDeviceDataYearMapper extends BaseMapper<SummaryElectricDeviceDataYear> {


	@Select("select * from summary_electric_device_data_year where id=#{summary_electric_device_dataId}")
	public SummaryElectricDeviceDataYear getBySummaryElectricDeviceDataId(@Param("summary_electric_device_dataId") String summary_electric_device_dataId);

	@Select("select * from summary_electric_device_data_year where code=#{code}")
	public SummaryElectricDeviceDataYear getBySummaryElectricDeviceDataCode(@Param("summary_electric_device_dataCode") String code);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_year where summary_year=#{year}")
	public Double getCountByYougong(@Param("year") int year);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_year where summary_year=#{year}")
	public Double getCountByWugong(@Param("year") int year);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_year ")
	public Double getCountByYougongAndTotal();
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_year ")
	public Double getCountByWugongAndTotal();

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_year where summary_year=#{year} and create_by=#{userId}")
	public Double getCountByYougongAndUser(@Param("userId") String userId,@Param("year") int year);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_year where summary_year=#{year} and create_by=#{userId}")
	public Double getCountByWugongAndUser(@Param("userId") String userId,@Param("year") int year);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_year  where create_by=#{userId}")
	public Double getCountByYougongAndTotalAndUser(@Param("userId") String userId);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_year  where create_by=#{userId}")
	public Double getCountByWugongAndTotalAndUser(@Param("userId") String userId);

}
