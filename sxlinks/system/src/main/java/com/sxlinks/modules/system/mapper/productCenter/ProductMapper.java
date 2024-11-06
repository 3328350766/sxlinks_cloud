package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.Product;
import org.apache.ibatis.annotations.Select;
import com.sxlinks.modules.system.entity.SysDepart;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface ProductMapper extends BaseMapper<Product> {


	@Select("select * from product where id=#{productId}")
	public Product getByProductId(@Param("productId") String productId);

	@Select("select * from product where product_code=#{code}")
	public Product getByProductCode(@Param("productCode") String code);

}
