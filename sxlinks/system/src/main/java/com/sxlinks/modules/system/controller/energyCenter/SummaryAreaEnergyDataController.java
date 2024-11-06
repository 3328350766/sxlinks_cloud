package com.sxlinks.modules.system.controller.energyCenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.energyCenter.*;
import com.sxlinks.modules.system.entity.energyCenter.SummaryAreaEnergyDataHour;
import com.sxlinks.modules.system.service.energyCenter.ISummaryAreaEnergyDataDayService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryAreaEnergyDataHourService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryAreaEnergyDataMonthService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryAreaEnergyDataYearService;
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
 * 区域能耗统计 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/energyCenter/summaryAreaEnergyData")
@Slf4j
@Api(value = "能源中心", tags = {"区域能耗统计数据"})
public class SummaryAreaEnergyDataController {
	@Autowired
	private ISummaryAreaEnergyDataHourService summaryAreaEnergyDataHourService;
	@Autowired
	private ISummaryAreaEnergyDataDayService summaryAreaEnergyDataDayService;
	@Autowired
	private ISummaryAreaEnergyDataMonthService summaryAreaEnergyDataMonthService;
	@Autowired
	private ISummaryAreaEnergyDataYearService summaryAreaEnergyDataYearService;

	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取区域能耗统计-时段数据分页列表", tags = "区域能耗统计数据")
	@RequestMapping(value = "/listHour", method = RequestMethod.GET)
	//IPage<SummaryAreaEnergyDataHour>
	public JSONObject queryPageListByHour(SummaryAreaEnergyDataHour data,
																		@RequestParam(name="startTime", defaultValue="") String startTime,
																		@RequestParam(name="endTime", defaultValue="") String endTime,
																		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<SummaryAreaEnergyDataHour>> result = new Result<IPage<SummaryAreaEnergyDataHour>>();
		QueryWrapper<SummaryAreaEnergyDataHour> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<SummaryAreaEnergyDataHour> page = new Page<SummaryAreaEnergyDataHour>(pageNo, pageSize);
		IPage<SummaryAreaEnergyDataHour> pageList = summaryAreaEnergyDataHourService.page(page, queryWrapper);

		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0;
		double totalElectricWugong=0;
		double totalWater=0;
		double totalGas=0;
		double totalEnergy=0;
		double totalLengliang=0;
		double totalReliang=0;

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		joTotalData.put("totalWater",totalWater); //总耗水
		joTotalData.put("totalGas",totalGas); //总耗气
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
	}

	@ApiOperation(value = "获取区域能耗统计-日数据分页列表", tags = "区域能耗统计数据")
	@RequestMapping(value = "/listDay", method = RequestMethod.GET)
	public JSONObject queryPageListByDay(SummaryAreaEnergyDataDay data,
																	  @RequestParam(name="startTime", defaultValue="") String startTime,
																	  @RequestParam(name="endTime", defaultValue="") String endTime,
																		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<SummaryAreaEnergyDataDay>> result = new Result<IPage<SummaryAreaEnergyDataDay>>();
		QueryWrapper<SummaryAreaEnergyDataDay> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<SummaryAreaEnergyDataDay> page = new Page<SummaryAreaEnergyDataDay>(pageNo, pageSize);
		IPage<SummaryAreaEnergyDataDay> pageList = summaryAreaEnergyDataDayService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0;
		double totalElectricWugong=0;
		double totalWater=0;
		double totalGas=0;
		double totalEnergy=0;
		double totalLengliang=0;
		double totalReliang=0;

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		joTotalData.put("totalWater",totalWater); //总耗水
		joTotalData.put("totalGas",totalGas); //总耗气
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);

		return jo;
		//return result;
	}
	@ApiOperation(value = "获取区域能耗统计-月数据分页列表", tags = "区域能耗统计数据")
	@RequestMapping(value = "/listMonth", method = RequestMethod.GET)
	public JSONObject queryPageListByMonth(SummaryAreaEnergyDataMonth data,
																		  @RequestParam(name="startTime", defaultValue="") String startTime,
																		  @RequestParam(name="endTime", defaultValue="") String endTime,
																		  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		  HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<SummaryAreaEnergyDataMonth>> result = new Result<IPage<SummaryAreaEnergyDataMonth>>();
		QueryWrapper<SummaryAreaEnergyDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<SummaryAreaEnergyDataMonth> page = new Page<SummaryAreaEnergyDataMonth>(pageNo, pageSize);
		IPage<SummaryAreaEnergyDataMonth> pageList = summaryAreaEnergyDataMonthService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0;
		double totalElectricWugong=0;
		double totalWater=0;
		double totalGas=0;
		double totalEnergy=0;
		double totalLengliang=0;
		double totalReliang=0;

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		joTotalData.put("totalWater",totalWater); //总耗水
		joTotalData.put("totalGas",totalGas); //总耗气
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取区域能耗统计-年数据分页列表", tags = "区域能耗统计数据")
	@RequestMapping(value = "/listYear", method = RequestMethod.GET)
	public JSONObject queryPageListByYear(SummaryAreaEnergyDataYear data,
																		@RequestParam(name="startTime", defaultValue="") String startTime,
																		@RequestParam(name="endTime", defaultValue="") String endTime,
																		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<SummaryAreaEnergyDataYear>> result = new Result<IPage<SummaryAreaEnergyDataYear>>();
		QueryWrapper<SummaryAreaEnergyDataYear> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<SummaryAreaEnergyDataYear> page = new Page<SummaryAreaEnergyDataYear>(pageNo, pageSize);
		IPage<SummaryAreaEnergyDataYear> pageList = summaryAreaEnergyDataYearService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0;
		double totalElectricWugong=0;
		double totalWater=0;
		double totalGas=0;
		double totalEnergy=0;
		double totalLengliang=0;
		double totalReliang=0;

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		joTotalData.put("totalWater",totalWater); //总耗水
		joTotalData.put("totalGas",totalGas); //总耗气
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}


	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询区域能耗统计-时段详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/queryByIdAndHour", method = RequestMethod.GET)
	public Result<SummaryAreaEnergyDataHour> queryByIdAndHour(@RequestParam(name="id",required=true) String id) {
		Result<SummaryAreaEnergyDataHour> result = new Result<SummaryAreaEnergyDataHour>();
		SummaryAreaEnergyDataHour deviceData = summaryAreaEnergyDataHourService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询区域能耗统计-日详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/queryByIdAndDay", method = RequestMethod.GET)
	public Result<SummaryAreaEnergyDataDay> queryByIdAndDay(@RequestParam(name="id",required=true) String id) {
		Result<SummaryAreaEnergyDataDay> result = new Result<SummaryAreaEnergyDataDay>();
		SummaryAreaEnergyDataDay deviceData = summaryAreaEnergyDataDayService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询区域能耗统计-月详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/queryByIdAndMonth", method = RequestMethod.GET)
	public Result<SummaryAreaEnergyDataMonth> queryByIdAndMonth(@RequestParam(name="id",required=true) String id) {
		Result<SummaryAreaEnergyDataMonth> result = new Result<SummaryAreaEnergyDataMonth>();
		SummaryAreaEnergyDataMonth deviceData = summaryAreaEnergyDataMonthService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询区域能耗统计-年详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/queryByIdAndYear", method = RequestMethod.GET)
	public Result<SummaryAreaEnergyDataYear> queryByIdAndYear(@RequestParam(name="id",required=true) String id) {
		Result<SummaryAreaEnergyDataYear> result = new Result<SummaryAreaEnergyDataYear>();
		SummaryAreaEnergyDataYear deviceData = summaryAreaEnergyDataYearService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 导出excel
	 * @param request
	 */
	@ApiOperation(value = "导出区域能耗统计-时段详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/exportXlsByHour", method = RequestMethod.POST)
	public ModelAndView exportXlsByHour(SummaryAreaEnergyDataHour deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryAreaEnergyDataHour> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryAreaEnergyDataHour> pageList = summaryAreaEnergyDataHourService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"区域能耗统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryAreaEnergyDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("区域能耗统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出区域能耗统计-日详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/exportXlsByDay", method = RequestMethod.POST)
	public ModelAndView exportXlsByDay(SummaryAreaEnergyDataDay deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryAreaEnergyDataDay> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryAreaEnergyDataDay> pageList = summaryAreaEnergyDataDayService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"区域能耗统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryAreaEnergyDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("区域能耗统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出区域能耗统计-月详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/exportXlsByMonth", method = RequestMethod.POST)
	public ModelAndView exportXlsByMonth(SummaryAreaEnergyDataMonth deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryAreaEnergyDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryAreaEnergyDataMonth> pageList = summaryAreaEnergyDataMonthService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"区域能耗统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryAreaEnergyDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("区域能耗统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出区域能耗统计-年详情", tags = "区域能耗统计数据")
	@RequestMapping(value = "/exportXlsByYear", method = RequestMethod.POST)
	public ModelAndView exportXlsByYear(SummaryAreaEnergyDataYear deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryAreaEnergyDataYear> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryAreaEnergyDataYear> pageList = summaryAreaEnergyDataYearService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"区域能耗统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryAreaEnergyDataYear.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("区域能耗统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return summaryElectricDeviceDataService.importExcelCheckRoleCode(file, params);
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
