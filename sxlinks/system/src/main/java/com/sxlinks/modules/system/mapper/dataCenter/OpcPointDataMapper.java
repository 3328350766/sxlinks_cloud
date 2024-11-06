package com.sxlinks.modules.system.mapper.dataCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.dataCenter.OpcPointData;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface OpcPointDataMapper extends BaseMapper<OpcPointData> {


	@Select("select * from opc_point_data where id=#{opc_point_dataId}")
	public SysDepart getByOpcPointDataId(@Param("opc_point_dataId") String opc_point_dataId);

	@Select("select * from opc_point_data where code=#{code}")
	public SysDepart getByOpcPointDataCode(@Param("opc_point_dataCode") String code);

}
