package com.sxlinks.modules.system.controller.dataCenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.dataCenter.DevicePropertyData;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;

import com.sxlinks.modules.system.entity.dataCenter.DevicePropertyDataHistory;
import com.sxlinks.modules.system.service.dataCenter.IDevicePropertyDataHistoryService;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备历史数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dataCenter/deviceDataHistory")
@Slf4j
@Api(value = "数据中心", tags = {"设备历史数据"})
public class DeviceDataHistoryController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyDataHistoryService devicePropertyDataHistoryService;
	@Resource
	private BaseCommonService baseCommonService;

	/**
	 * 分页列表查询
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取设备实时数据分页列表", tags = "设备历史数据")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONObject queryPageList(Device device,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		Result<IPage<Device>> result = new Result<IPage<Device>>();
		JSONObject jo= new JSONObject(); //输出的json数据
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		queryWrapper.eq("del_flag",0);
		//queryWrapper.eq("active_status","1"); //在线数据
		queryWrapper.ne("node_type","GATEWAY");	//排除网关设备
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());

		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		List lsDevice=pageList.getRecords();
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<lsDevice.size();i++){
			Device d=(Device)lsDevice.get(i);
			JSONObject joDevice= JSON.parseObject(JSON.toJSONString(d));
			List<DeviceRtResDto> lsPropertyData = deviceService.queryRtByDevCode(d.getDeviceCode());
			joDevice.put("lsPropertyData",lsPropertyData);
			jsonArray.add(joDevice);
		}
//		result.setSuccess(true);
//		result.setResult(pageList);
		jo.put("lsData",jsonArray);
		jo.put("total",pageList.getTotal());
		jo.put("pages",pageList.getPages());
		jo.put("size",pageList.getSize());
		jo.put("current",pageList.getCurrent());
		jo.put("success",true);//这个重要，不然页面要判断成功返回参数的关键
		int deviceCount=0;	//设备总数
		QueryWrapper queryDevice=new QueryWrapper<Device>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDevice.eq("create_by",user.getId());
		deviceCount=deviceService.count(queryDevice);
		jo.put("deviceCount",deviceCount);

		int deviceEnableCount=0;	//设备激活总数
		QueryWrapper queryDeviceEnable=new QueryWrapper<Device>().eq("enable_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceEnable.eq("create_by",user.getId());
		deviceEnableCount=deviceService.count(queryDeviceEnable);
		jo.put("deviceEnableCount",deviceEnableCount);

		int deviceOnlineCount=0;	//设备在线总数
		QueryWrapper queryDeviceOnline=new QueryWrapper<Device>().eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOnline.eq("create_by",user.getId());
		deviceOnlineCount=deviceService.count(queryDeviceOnline);
		jo.put("deviceOnlineCount",deviceOnlineCount);

//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取设备历史数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return jo;
	}



	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除设备实时数据", tags = "设备历史数据")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		devicePropertyDataHistoryService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除设备实时数据", tags = "设备历史数据")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<DevicePropertyDataHistory> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DevicePropertyDataHistory> result = new Result<DevicePropertyDataHistory>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中设备历史数据！");
		}else {
			devicePropertyDataHistoryService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除设备历史数据成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询设备实时数据详情", tags = "设备历史数据")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<DevicePropertyDataHistory> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DevicePropertyDataHistory> result = new Result<DevicePropertyDataHistory>();
		DevicePropertyDataHistory sysrole = devicePropertyDataHistoryService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有设备的实时数据", tags = "设备历史数据")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public JSONObject queryAll(Device device,HttpServletRequest req) {
		Result<IPage<Device>> result = new Result<IPage<Device>>();
		JSONObject jo= new JSONObject(); //输出的json数据
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		queryWrapper.eq("del_flag",0);
		//queryWrapper.eq("active_status","1"); //在线数据
		queryWrapper.ne("node_type","GATEWAY");	//排除网关设备
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Device> page = new Page<Device>(1, 10000); //最大查询1万个
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		List lsDevice=pageList.getRecords();
		JSONArray jsonArray=new JSONArray();
		for(int i=0;i<lsDevice.size();i++){
			Device d=(Device)lsDevice.get(i);
			JSONObject joDevice=JSON.parseObject(JSON.toJSONString(d));
			List<DeviceRtResDto> lsPropertyData = deviceService.queryRtByDevCode(d.getDeviceCode());
			joDevice.put("lsPropertyData",lsPropertyData);
			jsonArray.add(joDevice);
		}
//		result.setSuccess(true);
//		result.setResult(pageList);
		jo.put("lsData",jsonArray);
		jo.put("total",pageList.getTotal());
		jo.put("pages",pageList.getPages());
		jo.put("size",pageList.getSize());
		jo.put("current",pageList.getCurrent());
		jo.put("success",true);//这个重要，不然页面要判断成功返回参数的关键
		int deviceCount=0;	//设备总数
		QueryWrapper queryDevice=new QueryWrapper<Device>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDevice.eq("create_by",user.getId());
		deviceCount=deviceService.count(queryDevice);
		jo.put("deviceCount",deviceCount);

		int deviceEnableCount=0;	//设备激活总数
		QueryWrapper queryDeviceEnable=new QueryWrapper<Device>().eq("enable_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceEnable.eq("create_by",user.getId());
		deviceEnableCount=deviceService.count(queryDeviceEnable);
		jo.put("deviceEnableCount",deviceEnableCount);

		int deviceOnlineCount=0;	//设备在线总数
		QueryWrapper queryDeviceOnline=new QueryWrapper<Device>().eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOnline.eq("create_by",user.getId());
		deviceOnlineCount=deviceService.count(queryDeviceOnline);
		jo.put("deviceOnlineCount",deviceOnlineCount);


		return jo;
	}

	/**
	 * 导出excel
	 * @param request
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(DevicePropertyDataHistory sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<DevicePropertyDataHistory> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<DevicePropertyDataHistory> pageList = devicePropertyDataHistoryService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"设备历史数据列表");
		mv.addObject(NormalExcelConstants.CLASS,DevicePropertyDataHistory.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("设备历史数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}

	/**
	 * 通过excel导入数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//return devicePropertyDataHistoryService.importExcelCheckRoleCode(file, params);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Result.error("文件导入失败:" + e.getMessage());
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return Result.error("文件导入失败！");
	}


}
