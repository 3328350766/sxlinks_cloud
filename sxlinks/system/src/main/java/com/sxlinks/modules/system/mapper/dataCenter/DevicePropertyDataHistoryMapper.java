package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.DevicePropertyDataHistory;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DevicePropertyDataHistoryMapper extends BaseMapper<DevicePropertyDataHistory> {


	@Select("select * from device_property_data_history where id=#{device_property_data_historyId}")
	public SysDepart getByDevicePropertyDataHistoryId(@Param("device_property_data_historyId") String device_property_data_historyId);

	@Select("select * from device_property_data_history where code=#{code}")
	public SysDepart getByDevicePropertyDataHistoryCode(@Param("device_property_data_historyCode") String code);

}
