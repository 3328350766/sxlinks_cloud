package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.ConditionShellRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ConditionShellRecordMapper extends BaseMapper<ConditionShellRecord> {


	@Select("select * from condition_shell_record where id=#{condition_shell_recordId}")
	public SysDepart getByConditionShellRecordId(@Param("condition_shell_recordId") String condition_shell_recordId);

	@Select("select * from condition_shell_record where code=#{code}")
	public SysDepart getByConditionShellRecordCode(@Param("condition_shell_recordCode") String code);

}
