package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SummaryAreaEnergyDataDay;
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
public interface SummaryAreaEnergyDataDayMapper extends BaseMapper<SummaryAreaEnergyDataDay> {


	@Select("select * from summary_area_energy_data_day where id=#{summary_electric_device_dataId}")
	public SysDepart getBySummaryElectricDeviceDataId(@Param("summary_electric_device_dataId") String summary_electric_device_dataId);

	@Select("select * from summary_area_energy_data_day where code=#{code}")
	public SysDepart getBySummaryElectricDeviceDataCode(@Param("summary_electric_device_dataCode") String code);

}
