package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.AlarmRecordHistory;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface AlarmRecordHistoryMapper extends BaseMapper<AlarmRecordHistory> {


	@Select("select * from alarm_record where id=#{alarm_recordId}")
	public SysDepart getByAlarmRecordHistoryId(@Param("alarm_recordId") String alarm_recordId);

	@Select("select * from alarm_record where code=#{code}")
	public SysDepart getByAlarmRecordHistoryCode(@Param("alarm_recordCode") String code);

}
