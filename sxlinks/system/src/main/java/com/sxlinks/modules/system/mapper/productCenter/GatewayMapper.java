package com.sxlinks.modules.system.mapper.productCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;

import com.sxlinks.modules.system.entity.productCenter.Gateway;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface GatewayMapper extends BaseMapper<Gateway> {


	@Select("select * from gateway where id=#{gatewayId}")
	public Gateway getByGatewayId(@Param("gatewayId") String gatewayId);

	@Select("select * from gateway where code=#{code}")
	public Gateway getByGatewayCode(@Param("gatewayCode") String code);

}
