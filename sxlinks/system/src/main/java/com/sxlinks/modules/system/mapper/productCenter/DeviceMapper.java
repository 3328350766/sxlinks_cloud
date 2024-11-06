package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.productCenter.Device;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DeviceMapper extends BaseMapper<Device> {


	@Select("select * from device where id=#{deviceId}")
	public Device getByDeviceId(@Param("deviceId") String deviceId);

	@Select("select * from device where device_code=#{code}")
	public Device getByDeviceCode(@Param("deviceCode") String code);

}
