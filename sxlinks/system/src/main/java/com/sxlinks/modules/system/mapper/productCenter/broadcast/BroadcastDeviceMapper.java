package com.sxlinks.modules.system.mapper.productCenter.broadcast;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface BroadcastDeviceMapper extends BaseMapper<BroadcastDevice> {


	@Select("select * from broadcast_device where id=#{broadcast_deviceId}")
	public BroadcastDevice getByBroadcastDeviceId(@Param("broadcast_deviceId") String broadcast_deviceId);

	@Select("select * from broadcast_device where broadcast_device_code=#{code}")
	public BroadcastDevice getByBroadcastDeviceCode(@Param("broadcast_deviceCode") String code);

}
