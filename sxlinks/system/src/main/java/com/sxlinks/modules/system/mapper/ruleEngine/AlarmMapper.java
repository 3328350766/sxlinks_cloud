package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.ruleEngine.Alarm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface AlarmMapper extends BaseMapper<Alarm> {

	@Select(value = {" <script>" +
			"select * from alarm where true " +
			"<if test=\"alarm.name != null\"> and name like concat('%','${alarm.name}','%')</if>" +
			"<if test=\"alarm.type != null\"> and type = '${alarm.type}'</if>" +
			" </script>"})
	IPage<Alarm> custom(IPage<Alarm> iPage,@Param(value = "alarm") Alarm alarm);

	@Select("select * from alarm where id=#{alarmId}")
	public Alarm getByAlarmId(@Param("alarmId") Long alarmId);

	@Select("select * from alarm where code=#{code}")
	public Alarm getByAlarmCode(@Param("alarmCode") String code);

	@Delete("DELETE FROM alarm WHERE id=#{id}")
	int del(Long id);


}
