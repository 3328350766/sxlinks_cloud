package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.productCenter.ModbusPoint;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ModbusPointMapper extends BaseMapper<ModbusPoint> {


	@Select("select * from modbus_point where id=#{modbusPointId}")
	public ModbusPoint getByModbusPointId(@Param("modbusPointId") String modbusPointId);

	@Select("select * from modbus_point where code=#{code}")
	public ModbusPoint getByModbusPointCode(@Param("modbusPointCode") String code);

}
