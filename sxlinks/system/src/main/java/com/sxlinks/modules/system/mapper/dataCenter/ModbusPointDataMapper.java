package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.ModbusPointData;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ModbusPointDataMapper extends BaseMapper<ModbusPointData> {


	@Select("select * from modbus_point_data where id=#{modbus_point_dataId}")
	public SysDepart getByModbusPointDataId(@Param("modbus_point_dataId") String modbus_point_dataId);

	@Select("select * from modbus_point_data where code=#{code}")
	public SysDepart getByModbusPointDataCode(@Param("modbus_point_dataCode") String code);

}
