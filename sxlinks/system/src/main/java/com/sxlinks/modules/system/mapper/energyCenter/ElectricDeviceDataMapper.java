package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;

import com.sxlinks.modules.system.entity.energyCenter.ElectricDeviceData;
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
public interface ElectricDeviceDataMapper extends BaseMapper<ElectricDeviceData> {


	@Select("select * from electric_device_data where id=#{electric_device_dataId}")
	public SysDepart getByElectricDeviceDataId(@Param("electric_device_dataId") String electric_device_dataId);

	@Select("select * from electric_device_data where code=#{code}")
	public SysDepart getByElectricDeviceDataCode(@Param("electric_device_dataCode") String code);

}
