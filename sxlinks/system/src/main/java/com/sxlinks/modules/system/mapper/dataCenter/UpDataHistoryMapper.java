package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.UpDataHistory;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface UpDataHistoryMapper extends BaseMapper<UpDataHistory> {


	@Select("select * from up_data_history where id=#{up_data_historyId}")
	public SysDepart getByUpDataHistoryId(@Param("up_data_historyId") String up_data_historyId);

	@Select("select * from up_data_history where code=#{code}")
	public SysDepart getByUpDataHistoryCode(@Param("up_data_historyCode") String code);

}
