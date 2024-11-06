package com.sxlinks.modules.system.controller.dashboard;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.modules.system.entity.energyCenter.AirconditionDeviceData;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.ruleEngine.BelongProduct;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import com.sxlinks.modules.system.service.ruleEngine.IBelongProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dashboard/userAnalysis")
@Slf4j
@Api(value = "首页接口", tags = {"首页数据"})
public class DashboardController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private IBelongProductService belongProductService;
	/**
	 * 分页列表查询

	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取首页数据", tags = "首页数据")
	@RequestMapping(value = "/queryData", method = RequestMethod.GET)
	public JSONObject queryData(HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		JSONObject jsonResult=new JSONObject();

		//QueryWrapper<AirconditionDeviceData> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());

		int productCount=0;	//产品总数
		QueryWrapper queryProduct=new QueryWrapper<Product>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryProduct.eq("create_by",user.getId());
		productCount=productService.count(queryProduct);
		jsonResult.put("productCount",productCount);

		int deviceCount=0;	//设备总数
		QueryWrapper queryDevice=new QueryWrapper<Device>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDevice.eq("create_by",user.getId());
		deviceCount=deviceService.count(queryDevice);
		jsonResult.put("deviceCount",deviceCount);

		int deviceEnableCount=0;	//设备激活总数
		QueryWrapper queryDeviceEnable=new QueryWrapper<Device>().eq("enable_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceEnable.eq("create_by",user.getId());
		deviceEnableCount=deviceService.count(queryDeviceEnable);
		jsonResult.put("deviceEnableCount",deviceEnableCount);

		int deviceOnlineCount=0;	//设备在线总数
		QueryWrapper queryDeviceOnline=new QueryWrapper<Device>().eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOnline.eq("create_by",user.getId());
		deviceOnlineCount=deviceService.count(queryDeviceOnline);
		jsonResult.put("deviceOnlineCount",deviceOnlineCount);

		int deviceOfflineCount=0;	//设备离线总数
		QueryWrapper queryDeviceOffline=new QueryWrapper<Device>().eq("active_status","0");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOffline.eq("create_by",user.getId());
		deviceOfflineCount=deviceService.count(queryDeviceOffline);
		jsonResult.put("deviceOfflineCount",deviceOfflineCount);

		int totalIoCount=0;	//流量-总输入输出次数
		jsonResult.put("totalIoCount",totalIoCount);
		int mqttReportCount=0;	//mqtt上报次数
		jsonResult.put("mqttReportCount",mqttReportCount);
		int mqttDownCount=0;	//mqtt下发次数
		jsonResult.put("mqttDownCount",mqttDownCount);

		int flowReportSize=0;	//上报流量Mb
		jsonResult.put("flowReportSize",flowReportSize);
		int flowDownSize=0;	//下发流量Mb
		jsonResult.put("flowDownSize",flowDownSize);

		int alarmRuleCount=0;	//报警规则总数
		jsonResult.put("alarmRuleCount",alarmRuleCount);
		int alarmMakeCount=0;	//报警次数
		jsonResult.put("alarmMakeCount",alarmMakeCount);
		int alarmCancelCount=0;	//消警次数
		jsonResult.put("alarmCancelCount",alarmCancelCount);

		int conditionRuleCount=0;	//联动规则总数
		jsonResult.put("conditionRuleCount",conditionRuleCount);
		int conditionMakeCount=0;	//设备联动次数
		jsonResult.put("conditionMakeCount",conditionMakeCount);

		int electricRuleCount=0;	//耗电规则数量
		QueryWrapper queryElectric=new QueryWrapper<BelongProduct>().eq("belong_code","electric");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryElectric.eq("create_by",user.getId());
		electricRuleCount=belongProductService.count(queryElectric);
		jsonResult.put("electricRuleCount",electricRuleCount);

		int waterRuleCount=0;	//耗水规则数量
		QueryWrapper queryWater=new QueryWrapper<BelongProduct>().eq("belong_code","water");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWater.eq("create_by",user.getId());
		waterRuleCount=belongProductService.count(queryWater);
		jsonResult.put("waterRuleCount",waterRuleCount);

		int gasRuleCount=0;	//耗气规则数量
		QueryWrapper queryGas=new QueryWrapper<BelongProduct>().eq("belong_code","gas");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryGas.eq("create_by",user.getId());
		gasRuleCount=belongProductService.count(queryGas);
		jsonResult.put("gasRuleCount",gasRuleCount);

		int electricUseCount=0;	//今日耗电
		jsonResult.put("electricUseCount",electricUseCount);
		int waterUseCount=0;	//今日耗水
		jsonResult.put("waterUseCount",waterUseCount);
		int gasUseCount=0;	//今日耗气
		jsonResult.put("gasUseCount",gasUseCount);

		return jsonResult;
	}



}
