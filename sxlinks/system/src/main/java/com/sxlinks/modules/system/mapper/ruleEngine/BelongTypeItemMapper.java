package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.BelongTypeItem;
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
public interface BelongTypeItemMapper extends BaseMapper<BelongTypeItem> {


	@Select("select * from belong_type_item where id=#{belong_type_itemId}")
	public SysDepart getByBelongTypeItemId(@Param("belong_type_itemId") String belong_type_itemId);

	@Select("select * from belong_type_item where code=#{code}")
	public SysDepart getByBelongTypeItemCode(@Param("belong_type_itemCode") String code);

}
