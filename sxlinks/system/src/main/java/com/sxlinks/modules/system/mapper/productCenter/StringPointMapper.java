package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.StringPoint;
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
public interface StringPointMapper extends BaseMapper<StringPoint> {


	@Select("select * from string_point where id=#{modbusPointId}")
	public StringPoint getByStringPointId(@Param("modbusPointId") String modbusPointId);

	@Select("select * from string_point where code=#{code}")
	public StringPoint getByStringPointCode(@Param("modbusPointCode") String code);

}
