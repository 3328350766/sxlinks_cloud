package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.Location;
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
public interface LocationMapper extends BaseMapper<Location> {


	@Select("select * from location where id=#{locationId}")
	public Location getByLocationId(@Param("locationId") String locationId);

	@Select("select * from location where location_code=#{code}")
	public Location getByLocationCode(@Param("locationCode") String code);

}
