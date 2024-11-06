package com.sxlinks.modules.system.mapper.energyCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.energyCenter.SafeDivideData;
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
public interface SafeDivideDataMapper extends BaseMapper<SafeDivideData> {


	@Select("select * from safe_divide_data where id=#{safe_divide_dataId}")
	public SysDepart getBySafeDivideDataId(@Param("safe_divide_dataId") String safe_divide_dataId);

	@Select("select * from safe_divide_data where code=#{code}")
	public SysDepart getBySafeDivideDataCode(@Param("safe_divide_dataCode") String code);

}