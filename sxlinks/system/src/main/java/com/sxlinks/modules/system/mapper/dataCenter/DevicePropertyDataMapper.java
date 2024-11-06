package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.DevicePropertyData;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DevicePropertyDataMapper extends BaseMapper<DevicePropertyData> {


	@Select("select * from device_property_data where id=#{device_property_dataId}")
	public SysDepart getByDevicePropertyDataId(@Param("device_property_dataId") String device_property_dataId);

	@Select("select * from device_property_data where code=#{code}")
	public SysDepart getByDevicePropertyDataCode(@Param("device_property_dataCode") String code);

}
