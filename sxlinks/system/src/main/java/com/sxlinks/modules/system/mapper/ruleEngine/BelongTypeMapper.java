package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.BelongType;
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
public interface BelongTypeMapper extends BaseMapper<BelongType> {


	@Select("select * from belong_type where id=#{belong_typeId}")
	public SysDepart getByBelongTypeId(@Param("belong_typeId") String belong_typeId);

	@Select("select * from belong_type where code=#{code}")
	public SysDepart getByBelongTypeCode(@Param("belong_typeCode") String code);

}
