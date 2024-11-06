package com.sxlinks.modules.system.mapper.productCenter.broadcast;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastFirm;
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
public interface BroadcastFirmMapper extends BaseMapper<BroadcastFirm> {


	@Select("select * from broadcast_firm where id=#{broadcast_firmId}")
	public BroadcastFirm getByBroadcastFirmId(@Param("broadcast_firmId") String broadcast_firmId);

	@Select("select * from broadcast_firm where broadcast_firm_code=#{code}")
	public BroadcastFirm getByBroadcastFirmCode(@Param("broadcast_firmCode") String code);

}
