package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.WaterDeviceData;
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
public interface WaterDeviceDataMapper extends BaseMapper<WaterDeviceData> {


	@Select("select * from water_device_data where id=#{water_device_dataId}")
	public SysDepart getByWaterDeviceDataId(@Param("water_device_dataId") String water_device_dataId);

	@Select("select * from water_device_data where code=#{code}")
	public SysDepart getByWaterDeviceDataCode(@Param("water_device_dataCode") String code);

}
