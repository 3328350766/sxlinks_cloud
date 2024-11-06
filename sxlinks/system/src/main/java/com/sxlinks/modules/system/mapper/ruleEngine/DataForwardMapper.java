package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.DataForward;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DataForwardMapper extends BaseMapper<DataForward> {


	@Select("select * from data_forward where id=#{data_forwardId}")
	public SysDepart getByDataForwardId(@Param("data_forwardId") String data_forwardId);

	@Select("select * from data_forward where code=#{code}")
	public SysDepart getByDataForwardCode(@Param("data_forwardCode") String code);

}
