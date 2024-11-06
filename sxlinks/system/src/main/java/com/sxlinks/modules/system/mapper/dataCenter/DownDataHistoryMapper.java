package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.DownDataHistory;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DownDataHistoryMapper extends BaseMapper<DownDataHistory> {


	@Select("select * from down_data_history where id=#{down_data_historyId}")
	public SysDepart getByDownDataHistoryId(@Param("down_data_historyId") String down_data_historyId);

	@Select("select * from down_data_history where code=#{code}")
	public SysDepart getByDownDataHistoryCode(@Param("down_data_historyCode") String code);

}
