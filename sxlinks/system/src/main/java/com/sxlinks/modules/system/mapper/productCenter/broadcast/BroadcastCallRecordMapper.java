package com.sxlinks.modules.system.mapper.productCenter.broadcast;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastCallRecord;
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
public interface BroadcastCallRecordMapper extends BaseMapper<BroadcastCallRecord> {


	@Select("select * from broadcast_call_record where id=#{broadcast_call_recordId}")
	public BroadcastCallRecord getByBroadcastCallRecordId(@Param("broadcast_call_recordId") String broadcast_call_recordId);

	@Select("select * from broadcast_call_record where broadcast_call_record_code=#{code}")
	public BroadcastCallRecord getByBroadcastCallRecordCode(@Param("broadcast_call_recordCode") String code);

}
