package com.sxlinks.modules.system.mapper.productCenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.DivideDeviceMap;
import com.sxlinks.modules.system.entity.productCenter.Product;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 * 
 * @Author: baba
 * @Since：   2022-06-02
 */
public interface DivideDeviceMapMapper extends BaseMapper<DivideDeviceMap> {

	@Select("select * from divide_device_map where id=#{divideId}")
	public DivideDeviceMap getByDivideId(@Param("divideId") String divideId);

	@Select("select * from divide_device_map where divide_code=#{divideCode}")
	public DivideDeviceMap getByDivideCode(@Param("divideCode") String divideCode);

	@Select("select b.* from divide_device_map a,device b where a.divide_id=#{divideId} and b.device_code=a.device_code")
	public List<Device> getDeviceListByDivideId(@Param("divideId") String divideId);

	@Select("select b.* from divide_device_map a,device b where a.divide_code=#{divideCode} and b.device_code=a.device_code")
	public List<Device> getDeviceListByDivideCode(@Param("divideCode") String divideCode);

}
