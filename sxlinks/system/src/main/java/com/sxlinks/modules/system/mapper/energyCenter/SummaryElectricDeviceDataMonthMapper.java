package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataMonth;
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
public interface SummaryElectricDeviceDataMonthMapper extends BaseMapper<SummaryElectricDeviceDataMonth> {


	@Select("select * from summary_electric_device_data_month where id=#{summary_electric_device_dataId}")
	public SummaryElectricDeviceDataMonth getBySummaryElectricDeviceDataId(@Param("summary_electric_device_dataId") String summary_electric_device_dataId);

	@Select("select * from summary_electric_device_data_month where code=#{code}")
	public SummaryElectricDeviceDataMonth getBySummaryElectricDeviceDataCode(@Param("summary_electric_device_dataCode") String code);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and  summary_month=#{month}")
	public Double getCountByYougong(@Param("year") int year,@Param("month") int month);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and  summary_month=#{month}")
	public Double getCountByWugong(@Param("year") int year,@Param("month") int month);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year}")
	public Double getCountByYougongAndYear(@Param("year") int year);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year}")
	public Double getCountByWugongAndYear(@Param("year") int year);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and  summary_month=#{month} and create_by=#{userId}")
	public Double getCountByYougongAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and  summary_month=#{month} and create_by=#{userId}")
	public Double getCountByWugongAndUser(@Param("userId") String userId,@Param("year") int year,@Param("month") int month);

	@Select("select cast(sum(yougong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and create_by=#{userId}")
	public Double getCountByYougongAndYearAndUser(@Param("userId") String userId,@Param("year") int year);
	@Select("select cast(sum(wugong) AS decimal(15,2)) from summary_electric_device_data_month where summary_year=#{year} and create_by=#{userId}")
	public Double getCountByWugongAndYearAndUser(@Param("userId") String userId,@Param("year") int year);


}
