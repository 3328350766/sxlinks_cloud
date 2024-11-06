package com.sxlinks.modules.system.mapper.productCenter.broadcast;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastAlarmRecord;
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
public interface BroadcastAlarmRecordMapper extends BaseMapper<BroadcastAlarmRecord> {


	@Select("select * from broadcast_alarm_record where id=#{broadcast_alarm_recordId}")
	public BroadcastAlarmRecord getByBroadcastAlarmRecordId(@Param("broadcast_alarm_recordId") String broadcast_alarm_recordId);

	@Select("select * from broadcast_alarm_record where broadcast_alarm_record_code=#{code}")
	public BroadcastAlarmRecord getByBroadcastAlarmRecordCode(@Param("broadcast_alarm_recordCode") String code);

}
