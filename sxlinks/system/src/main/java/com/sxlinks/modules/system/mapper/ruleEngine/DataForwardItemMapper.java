package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.DataForwardItem;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DataForwardItemMapper extends BaseMapper<DataForwardItem> {


	@Select("select * from data_forward_item where id=#{data_forward_itemId}")
	public SysDepart getByDataForwardItemId(@Param("data_forward_itemId") String data_forward_itemId);

	@Select("select * from data_forward_item where code=#{code}")
	public SysDepart getByDataForwardItemCode(@Param("data_forward_itemCode") String code);

}
