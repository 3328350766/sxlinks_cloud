package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.DeviceCamera;
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
public interface DeviceCameraMapper extends BaseMapper<DeviceCamera> {


	@Select("select * from device_camera where id=#{device_cameraId}")
	public DeviceCamera getByDeviceCameraId(@Param("device_cameraId") String device_cameraId);

	@Select("select * from device_camera where device_camera_code=#{code}")
	public DeviceCamera getByDeviceCameraCode(@Param("device_cameraCode") String code);

}
