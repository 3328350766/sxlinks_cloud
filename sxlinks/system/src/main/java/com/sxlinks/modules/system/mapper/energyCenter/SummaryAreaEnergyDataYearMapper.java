package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryAreaEnergyDataYear;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataDay;
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
public interface SummaryAreaEnergyDataYearMapper extends BaseMapper<SummaryAreaEnergyDataYear> {


	@Select("select * from summary_area_energy_data_year where id=#{summary_electric_device_dataId}")
	public SysDepart getBySummaryElectricDeviceDataId(@Param("summary_electric_device_dataId") String summary_electric_device_dataId);

	@Select("select * from summary_area_energy_data_year where code=#{code}")
	public SysDepart getBySummaryElectricDeviceDataCode(@Param("summary_electric_device_dataCode") String code);

}
