package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.productCenter.ProductType;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

	@Select("select * from product_type where id=#{productId}")
	public ProductType getByProductTypeId(@Param("productId") String productId);

	@Select("select * from product_type where code=#{code}")
	public ProductType getByProductTypeCode(@Param("productCode") String code);

}
