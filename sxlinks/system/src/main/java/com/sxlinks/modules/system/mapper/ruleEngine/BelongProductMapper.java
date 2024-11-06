package com.sxlinks.modules.system.mapper.ruleEngine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.SysDepart;
import com.sxlinks.modules.system.entity.ruleEngine.BelongProduct;
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
public interface BelongProductMapper extends BaseMapper<BelongProduct> {


	@Select("select * from belong_product where id=#{belong_productId}")
	public SysDepart getByBelongProductId(@Param("belong_productId") String belong_productId);

	@Select("select * from belong_product where code=#{code}")
	public SysDepart getByBelongProductCode(@Param("belong_productCode") String code);

}
