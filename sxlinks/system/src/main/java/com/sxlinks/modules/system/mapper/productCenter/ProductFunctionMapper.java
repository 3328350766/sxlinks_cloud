package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.ProductFunction;
import com.sxlinks.modules.system.entity.productCenter.ProductProperty;
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
public interface ProductFunctionMapper extends BaseMapper<ProductFunction> {


	@Select("select * from product_function where id=#{productFunctionCode}")
	public ProductProperty getByProductPropertyId(@Param("productFunctionId") String productFunctionId);

	@Select("select * from product_function where identifier=#{code}")
	public ProductProperty getByProductPropertyCode(@Param("productFunctionCode") String code);

}
