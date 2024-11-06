package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.DataForwardRecordHistory;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DataForwardRecordHistoryMapper extends BaseMapper<DataForwardRecordHistory> {


	@Select("select * from data_foward_record_history where id=#{data_foward_record_historyId}")
	public SysDepart getByDataForwardRecordHistoryId(@Param("data_foward_record_historyId") String data_foward_record_historyId);

	@Select("select * from data_foward_record_history where code=#{code}")
	public SysDepart getByDataForwardRecordHistoryCode(@Param("data_foward_record_historyCode") String code);

}
