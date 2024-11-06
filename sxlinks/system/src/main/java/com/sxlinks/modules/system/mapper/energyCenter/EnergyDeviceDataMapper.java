package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.EnergyDeviceData;
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
public interface EnergyDeviceDataMapper extends BaseMapper<EnergyDeviceData> {


	@Select("select * from energy_device_data where id=#{energy_device_dataId}")
	public SysDepart getByEnergyDeviceDataId(@Param("energy_device_dataId") String energy_device_dataId);

	@Select("select * from energy_device_data where code=#{code}")
	public SysDepart getByEnergyDeviceDataCode(@Param("energy_device_dataCode") String code);

}
