package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.ConditionShellItem;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ConditionShellItemMapper extends BaseMapper<ConditionShellItem> {


	@Select("select * from condition_shell_item where id=#{condition_shell_itemId}")
	public SysDepart getByConditionShellItemId(@Param("condition_shell_itemId") String condition_shell_itemId);

	@Select("select * from condition_shell_item where code=#{code}")
	public SysDepart getByConditionShellItemCode(@Param("condition_shell_itemCode") String code);

}
