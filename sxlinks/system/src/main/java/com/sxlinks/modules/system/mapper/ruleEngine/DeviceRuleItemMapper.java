package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.DeviceRuleItem;
import com.sxlinks.modules.system.entity.ruleEngine.DeviceRuleItem;
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
public interface DeviceRuleItemMapper extends BaseMapper<DeviceRuleItem> {


	@Select("select * from device_rule_item where id=#{device_rule_itemId}")
	public SysDepart getByDeviceRuleItemId(@Param("device_rule_itemId") String device_rule_itemId);

	@Select("select * from device_rule_item where code=#{code}")
	public SysDepart getByDeviceRuleItemCode(@Param("device_rule_itemCode") String code);

}
