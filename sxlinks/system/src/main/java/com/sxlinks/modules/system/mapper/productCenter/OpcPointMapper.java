package com.sxlinks.modules.system.mapper.productCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.productCenter.OpcPoint;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface OpcPointMapper extends BaseMapper<OpcPoint> {


	@Select("select * from opc_point where id=#{opcPointId}")
	public OpcPoint getByOpcPointId(@Param("opcPointId") String opcPointId);

	@Select("select * from opc_point where code=#{code}")
	public OpcPoint getByOpcPointCode(@Param("opcPointCode") String code);

}
