package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.EnvironmentDivideData;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface EnvironmentDivideDataMapper extends BaseMapper<EnvironmentDivideData> {


	@Select("select * from environment_divide_data where id=#{environment_divide_dataId}")
	public SysDepart getByEnvironmentDivideDataId(@Param("environment_divide_dataId") String environment_divide_dataId);

	@Select("select * from environment_divide_data where code=#{code}")
	public SysDepart getByEnvironmentDivideDataCode(@Param("environment_divide_dataCode") String code);

}
