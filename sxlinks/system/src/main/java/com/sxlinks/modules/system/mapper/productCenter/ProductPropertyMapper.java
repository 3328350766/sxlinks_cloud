package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ProductPropertyMapper extends BaseMapper<ProductProperty> {


	@Select("select * from product_property where id=#{productPropertyId}")
	public ProductProperty getByProductPropertyId(@Param("productPropertyId") String productPropertyId);

	@Select("select * from product_property where identifier=#{code}")
	public ProductProperty getByProductPropertyCode(@Param("productPropertyCode") String code);

}
