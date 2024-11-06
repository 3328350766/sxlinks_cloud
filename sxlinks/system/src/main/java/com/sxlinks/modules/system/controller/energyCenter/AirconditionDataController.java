package com.sxlinks.modules.system.controller.energyCenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.energyCenter.AirconditionDeviceData;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.service.energyCenter.IAirconditionDeviceDataService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 空调数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/energyCenter/airconditionData")
@Slf4j
@Api(value = "能源中心", tags = {"空调实时数据"})
public class AirconditionDataController {
	@Autowired
	private IAirconditionDeviceDataService airconditionDeviceDataService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IDeviceService deviceService;

	/**
	 * 分页列表查询
	 * @param data
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取空调实时数据列表", tags = "空调实时数据")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONObject queryList(AirconditionDeviceData data,

														HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		JSONObject jsonResult=new JSONObject();
		Result<IPage<AirconditionDeviceData>> result = new Result<IPage<AirconditionDeviceData>>();
		JSONArray jaResult=new JSONArray();
		//QueryWrapper<AirconditionDeviceData> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//查找相关的离心机

		Product p=productService.getOne(new QueryWrapper<Product>().eq("product_name","离心机"));
		//查找离心机相关的设备信息,当前用户拥有的
		QueryWrapper queryWrapper=new QueryWrapper<Device>().eq("product_code",p.getProductCode());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		List lsDevice=deviceService.list(queryWrapper);
		for(int i=0;i<lsDevice.size();i++){
			Device d=(Device)lsDevice.get(i);
			JSONObject joDevice=(JSONObject) JSON.toJSON(d);
			//查询离心机相关的产品数据
			List<DeviceRtResDto> lsPropertyData = deviceService.queryRtByDevCode(d.getDeviceCode());
			joDevice.put("lsPropertyData",lsPropertyData);
			jaResult.add(joDevice);
		}
		jsonResult.put("lsDeviceData",jaResult);
		return jsonResult;
	}


	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询实时空调详情", tags = "空调实时数据")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<AirconditionDeviceData> queryById(@RequestParam(name="id",required=true) String id) {
		Result<AirconditionDeviceData> result = new Result<AirconditionDeviceData>();
		AirconditionDeviceData sysrole = airconditionDeviceDataService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有空调", tags = "空调实时数据")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<AirconditionDeviceData>> queryAll() {
		Result<List<AirconditionDeviceData>> result = new Result<>();

		List<AirconditionDeviceData> list = airconditionDeviceDataService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}



}
