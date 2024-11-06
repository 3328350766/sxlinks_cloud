package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.JsonPoint;
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
public interface JsonPointMapper extends BaseMapper<JsonPoint> {


	@Select("select * from json_point where id=#{modbusPointId}")
	public JsonPoint getByJsonPointId(@Param("modbusPointId") String modbusPointId);

	@Select("select * from json_point where code=#{code}")
	public JsonPoint getByJsonPointCode(@Param("modbusPointCode") String code);

}
