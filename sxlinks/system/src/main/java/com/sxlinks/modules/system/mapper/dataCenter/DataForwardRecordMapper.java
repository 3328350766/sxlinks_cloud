package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.DataForwardRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DataForwardRecordMapper extends BaseMapper<DataForwardRecord> {


	@Select("select * from data_forward_record where id=#{data_forward_recordId}")
	public SysDepart getByDataForwardRecordId(@Param("data_forward_recordId") String data_forward_recordId);

	@Select("select * from data_forward_record where code=#{code}")
	public SysDepart getByDataForwardRecordCode(@Param("data_forward_recordCode") String code);

}
