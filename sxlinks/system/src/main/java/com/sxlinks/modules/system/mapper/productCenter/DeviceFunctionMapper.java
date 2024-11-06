package com.sxlinks.modules.system.mapper.productCenter;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.DeviceFunction;
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
public interface DeviceFunctionMapper extends BaseMapper<DeviceFunction> {

	@Select("select * from device_function where id=#{deviceFunctionId}")
	public DeviceFunction getByFunctionId(@Param("deviceFunctionId") String deviceFunctionId);

	@Select("select * from device_function where identifier=#{code}")
	public DeviceFunction getByFunctionCode(@Param("deviceFunctionCode") String code);

}
