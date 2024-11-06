package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.AlarmItem;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface AlarmItemMapper extends BaseMapper<AlarmItem> {


	@Select("select * from alarm_item where id=#{alarm_itemId}")
	public SysDepart getByAlarmItemId(@Param("alarm_itemId") String alarm_itemId);

	@Select("select * from alarm_item where code=#{code}")
	public SysDepart getByAlarmItemCode(@Param("alarm_itemCode") String code);

}
