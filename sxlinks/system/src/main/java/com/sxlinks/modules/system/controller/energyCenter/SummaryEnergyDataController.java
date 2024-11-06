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
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataDay;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataMonth;
import com.sxlinks.modules.system.entity.energyCenter.SummaryEnergyDeviceDataYear;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryEnergyDeviceDataDayMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryEnergyDeviceDataHourMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryEnergyDeviceDataMonthMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryEnergyDeviceDataYearMapper;
import com.sxlinks.modules.system.service.energyCenter.ISummaryEnergyDeviceDataDayService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryEnergyDeviceDataHourService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryEnergyDeviceDataMonthService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryEnergyDeviceDataYearService;
import com.sxlinks.utils.DateUtil;
import com.sxlinks.utils.StringUtils;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 能耗表数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/energyCenter/summaryEnergyData")
@Slf4j
@Api(value = "能源中心", tags = {"能耗表统计数据"})
public class SummaryEnergyDataController {
	@Autowired
	private ISummaryEnergyDeviceDataHourService summaryEnergyDeviceDataHourService;
	@Autowired
	private ISummaryEnergyDeviceDataDayService summaryEnergyDeviceDataDayService;
	@Autowired
	private ISummaryEnergyDeviceDataMonthService summaryEnergyDeviceDataMonthService;
	@Autowired
	private ISummaryEnergyDeviceDataYearService summaryEnergyDeviceDataYearService;
	
	@Resource
	private SummaryEnergyDeviceDataHourMapper hourDao;
	@Resource
	private SummaryEnergyDeviceDataDayMapper dayDao;
	@Resource
	private SummaryEnergyDeviceDataMonthMapper monthDao;
	@Resource
	private SummaryEnergyDeviceDataYearMapper yearDao;

	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取能耗表统计-时段数据分页列表", tags = "能耗表统计数据")
	@RequestMapping(value = "/listHour", method = RequestMethod.GET)
	public JSONObject queryPageListByHour(SummaryEnergyDeviceDataHour data,
																		  @RequestParam(name="startTime", defaultValue="") String startTime,
																		  @RequestParam(name="endTime", defaultValue="") String endTime,
																	@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																	@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																	HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		int year=Integer.valueOf(DateUtil.getCurrentYear());
		int month=Integer.valueOf(DateUtil.getCurrentMonth());
		int day=Integer.valueOf(DateUtil.getCurrentDay());
		int hour=Integer.valueOf(DateUtil.getCurrentHour());

		Result<IPage<SummaryEnergyDeviceDataHour>> result = new Result<IPage<SummaryEnergyDeviceDataHour>>();
		QueryWrapper<SummaryEnergyDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));
		}
		queryWrapper.eq("summary_year",year);
		queryWrapper.eq("summary_month",month);
		queryWrapper.eq("summary_day",day);

		Page<SummaryEnergyDeviceDataHour> page = new Page<SummaryEnergyDeviceDataHour>(pageNo, pageSize);
		IPage<SummaryEnergyDeviceDataHour> pageList = summaryEnergyDeviceDataHourService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalEnergy=0d;
		double totalLengliang=0d;
		double totalReliang=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalEnergy = hourDao.getCountByPowerAndDayAndUser(user.getId(), year, month, day);
			} else {
				totalEnergy = hourDao.getCountByPowerAndDay(year, month, day);

			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}

	@ApiOperation(value = "获取能耗表统计-日数据分页列表", tags = "能耗表统计数据")
	@RequestMapping(value = "/listDay", method = RequestMethod.GET)
	public JSONObject queryPageListByDay(SummaryEnergyDeviceDataDay data,
																		@RequestParam(name="startTime", defaultValue="") String startTime,
																		@RequestParam(name="endTime", defaultValue="") String endTime,
																		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		int year=Integer.valueOf(DateUtil.getCurrentYear());
		int month=Integer.valueOf(DateUtil.getCurrentMonth());
		int day=Integer.valueOf(DateUtil.getCurrentDay());
		int hour=Integer.valueOf(DateUtil.getCurrentHour());

		Result<IPage<SummaryEnergyDeviceDataDay>> result = new Result<IPage<SummaryEnergyDeviceDataDay>>();
		QueryWrapper<SummaryEnergyDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));
		}
		queryWrapper.eq("summary_year",year);
		queryWrapper.eq("summary_month",month);
//		queryWrapper.eq("summary_day",day);

		Page<SummaryEnergyDeviceDataDay> page = new Page<SummaryEnergyDeviceDataDay>(pageNo, pageSize);
		IPage<SummaryEnergyDeviceDataDay> pageList = summaryEnergyDeviceDataDayService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);


		double totalEnergy=0d;
		double totalLengliang=0d;
		double totalReliang=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalEnergy = dayDao.getCountByPowerAndMonthAndUser(user.getId(), year, month);
			} else {
				totalEnergy = dayDao.getCountByPowerAndMonth(year, month);

			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取能耗表统计-月数据分页列表", tags = "能耗表统计数据")
	@RequestMapping(value = "/listMonth", method = RequestMethod.GET)
	public JSONObject queryPageListByMonth(SummaryEnergyDeviceDataMonth data,
																			@RequestParam(name="startTime", defaultValue="") String startTime,
																			@RequestParam(name="endTime", defaultValue="") String endTime,
																		  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		  HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		int year=Integer.valueOf(DateUtil.getCurrentYear());
		int month=Integer.valueOf(DateUtil.getCurrentMonth());
		int day=Integer.valueOf(DateUtil.getCurrentDay());
		int hour=Integer.valueOf(DateUtil.getCurrentHour());

		Result<IPage<SummaryEnergyDeviceDataMonth>> result = new Result<IPage<SummaryEnergyDeviceDataMonth>>();
		QueryWrapper<SummaryEnergyDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));
		}
		queryWrapper.eq("summary_year",year);
//		queryWrapper.eq("summary_month",month);
//		queryWrapper.eq("summary_day",day);

		Page<SummaryEnergyDeviceDataMonth> page = new Page<SummaryEnergyDeviceDataMonth>(pageNo, pageSize);
		IPage<SummaryEnergyDeviceDataMonth> pageList = summaryEnergyDeviceDataMonthService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalEnergy=0d;
		double totalLengliang=0d;
		double totalReliang=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalEnergy = monthDao.getCountByPowerAndYearAndUser(user.getId(), year);
			} else {
				totalEnergy = monthDao.getCountByPowerAndYear(year);

			}
		}

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalEnergy",totalEnergy); //总能耗-功耗
		joTotalData.put("totalLengliang",totalLengliang); //总能耗-冷量
		joTotalData.put("totalReliang",totalReliang); //总能耗-热量
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取能耗表统计-年数据分页列表", tags = "能耗表统计数据")
	@RequestMapping(value = "/listYear", method = RequestMethod.GET)
	public JSONObject queryPageListByYear(SummaryEnergyDeviceDataYear data,
																		  @RequestParam(name="startTime", defaultValue="") String startTime,
																		  @RequestParam(name="endTime", defaultValue="") String endTime,
																		  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																		  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																		  HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		int year=Integer.valueOf(DateUtil.getCurrentYear());
		int month=Integer.valueOf(DateUtil.getCurrentMonth());
		int day=Integer.valueOf(DateUtil.getCurrentDay());
		int hour=Integer.valueOf(DateUtil.getCurrentHour());

		Result<IPage<SummaryEnergyDeviceDataYear>> result = new Result<IPage<SummaryEnergyDeviceDataYear>>();
		QueryWrapper<SummaryEnergyDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));
		}
//		queryWrapper.eq("summary_year",year);
//		queryWrapper.eq("summary_month",month);
//		queryWrapper.eq("summary_day",day);

		Page<SummaryEnergyDeviceDataYear> page = new Page<SummaryEnergyDeviceDataYear>(pageNo, pageSize);
		IPage<SummaryEnergyDeviceDataYear> pageList = summaryEnergyDeviceDataYearService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalEnergy=0d;
		double totalLengliang=0d;
		double totalReliang=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalEnergy = yearDao.getCountByPowerAndTotalAndUser(user.getId());
			} else {
				totalEnergy = yearDao.getCountByPowerAndTotal();

			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
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
	@ApiOperation(value = "根据id查询能耗表统计-时段详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/queryByIdAndHour", method = RequestMethod.GET)
	public Result<SummaryEnergyDeviceDataHour> queryByIdAndHour(@RequestParam(name="id",required=true) String id) {
		Result<SummaryEnergyDeviceDataHour> result = new Result<SummaryEnergyDeviceDataHour>();
		SummaryEnergyDeviceDataHour deviceData = summaryEnergyDeviceDataHourService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询能耗表统计-日详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/queryByIdAndDay", method = RequestMethod.GET)
	public Result<SummaryEnergyDeviceDataDay> queryByIdAndDay(@RequestParam(name="id",required=true) String id) {
		Result<SummaryEnergyDeviceDataDay> result = new Result<SummaryEnergyDeviceDataDay>();
		SummaryEnergyDeviceDataDay deviceData = summaryEnergyDeviceDataDayService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询能耗表统计-月详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/queryByIdAndMonth", method = RequestMethod.GET)
	public Result<SummaryEnergyDeviceDataMonth> queryByIdAndMonth(@RequestParam(name="id",required=true) String id) {
		Result<SummaryEnergyDeviceDataMonth> result = new Result<SummaryEnergyDeviceDataMonth>();
		SummaryEnergyDeviceDataMonth deviceData = summaryEnergyDeviceDataMonthService.getById(id);
		if(deviceData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(deviceData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询能耗表统计-年详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/queryByIdAndYear", method = RequestMethod.GET)
	public Result<SummaryEnergyDeviceDataYear> queryByIdAndYear(@RequestParam(name="id",required=true) String id) {
		Result<SummaryEnergyDeviceDataYear> result = new Result<SummaryEnergyDeviceDataYear>();
		SummaryEnergyDeviceDataYear deviceData = summaryEnergyDeviceDataYearService.getById(id);
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
	@ApiOperation(value = "导出能耗表统计-时段详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/exportXlsByHour", method = RequestMethod.POST)
	public ModelAndView exportXlsByHour(SummaryEnergyDeviceDataHour deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryEnergyDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryEnergyDeviceDataHour> pageList = summaryEnergyDeviceDataHourService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"能耗表数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryEnergyDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("能耗表数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出能耗表统计-日详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/exportXlsByDay", method = RequestMethod.POST)
	public ModelAndView exportXlsByDay(SummaryEnergyDeviceDataDay deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryEnergyDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryEnergyDeviceDataDay> pageList = summaryEnergyDeviceDataDayService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"能耗表数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryEnergyDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("能耗表数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出能耗表统计-月详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/exportXlsByMonth", method = RequestMethod.POST)
	public ModelAndView exportXlsByMonth(SummaryEnergyDeviceDataMonth deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryEnergyDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryEnergyDeviceDataMonth> pageList = summaryEnergyDeviceDataMonthService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"能耗表数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryEnergyDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("能耗表数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出能耗表统计-年详情", tags = "能耗表统计数据")
	@RequestMapping(value = "/exportXlsByYear", method = RequestMethod.POST)
	public ModelAndView exportXlsByYear(SummaryEnergyDeviceDataYear deviceData, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryEnergyDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(deviceData, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryEnergyDeviceDataYear> pageList = summaryEnergyDeviceDataYearService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"能耗表数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryEnergyDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("能耗表数据列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return summaryEnergyDeviceDataService.importExcelCheckRoleCode(file, params);
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
