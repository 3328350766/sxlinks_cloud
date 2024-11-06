package com.sxlinks.modules.system.mapper.productCenter.broadcast;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastGateway;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface BroadcastGatewayMapper extends BaseMapper<BroadcastGateway> {


	@Select("select * from broadcast_gateway where id=#{broadcast_gatewayId}")
	public BroadcastGateway getByBroadcastGatewayId(@Param("broadcast_gatewayId") String broadcast_gatewayId);

	@Select("select * from broadcast_gateway where broadcast_gateway_code=#{code}")
	public BroadcastGateway getByBroadcastGatewayCode(@Param("broadcast_gatewayCode") String code);

}
