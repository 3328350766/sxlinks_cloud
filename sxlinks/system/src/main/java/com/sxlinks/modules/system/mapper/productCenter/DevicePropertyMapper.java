package com.sxlinks.modules.system.mapper.productCenter;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import com.sxlinks.modules.system.entity.productCenter.DeviceProperty;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DevicePropertyMapper extends BaseMapper<DeviceProperty> {


	@Select("select * from device_property where id=#{devicePropertyId}")
	public DeviceProperty getByPropertyId(@Param("devicePropertyId") String devicePropertyId);

	@Select("select * from device_property where identifier=#{code}")
	public DeviceProperty getByPropertyCode(@Param("devicePropertyCode") String code);

}
