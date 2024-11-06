package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.ConditionShell;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ConditionShellMapper extends BaseMapper<ConditionShell> {


	@Select("select * from condition_shell where id=#{condition_shellId}")
	public SysDepart getByConditionShellId(@Param("condition_shellId") String condition_shellId);

	@Select("select * from condition_shell where code=#{code}")
	public SysDepart getByConditionShellCode(@Param("condition_shellCode") String code);

}
