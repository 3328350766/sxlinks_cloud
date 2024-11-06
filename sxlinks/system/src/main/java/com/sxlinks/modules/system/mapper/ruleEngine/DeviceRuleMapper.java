package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;

import com.sxlinks.modules.system.entity.ruleEngine.DeviceRule;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DeviceRuleMapper extends BaseMapper<DeviceRule> {


	@Select("select * from device_rule where id=#{device_ruleId}")
	public SysDepart getByRuleId(@Param("device_ruleId") String device_ruleId);

	@Select("select * from device_rule where code=#{code}")
	public SysDepart getByRuleCode(@Param("device_ruleCode") String code);

}
