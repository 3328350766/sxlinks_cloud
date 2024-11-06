package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.AirconditionDeviceData;
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
public interface AirconditionDeviceDataMapper extends BaseMapper<AirconditionDeviceData> {


	@Select("select * from aircondition_device_data where id=#{aircondition_device_dataId}")
	public SysDepart getByAirconditionDeviceDataId(@Param("aircondition_device_dataId") String aircondition_device_dataId);

	@Select("select * from aircondition_device_data where code=#{code}")
	public SysDepart getByAirconditionDeviceDataCode(@Param("aircondition_device_dataCode") String code);

}
