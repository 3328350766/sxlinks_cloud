package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.Divide;
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
public interface DivideMapper extends BaseMapper<Divide> {


	@Select("select * from divide where id=#{divideId}")
	public Divide getByDivideId(@Param("divideId") String divideId);

	@Select("select * from divide where code=#{code}")
	public Divide getByDivideCode(@Param("divideCode") String code);

}
