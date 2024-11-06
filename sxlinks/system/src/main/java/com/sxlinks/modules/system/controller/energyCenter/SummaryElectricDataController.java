package com.sxlinks.modules.system.controller.energyCenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.DateUtils;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataDay;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataMonth;
import com.sxlinks.modules.system.entity.energyCenter.SummaryElectricDeviceDataYear;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryElectricDeviceDataDayMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryElectricDeviceDataHourMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryElectricDeviceDataMonthMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryElectricDeviceDataYearMapper;
import com.sxlinks.modules.system.service.energyCenter.ISummaryElectricDeviceDataDayService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryElectricDeviceDataHourService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryElectricDeviceDataMonthService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryElectricDeviceDataYearService;
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
 * 电表统计数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/energyCenter/summaryElectricData")
@Slf4j
@Api(value = "能源中心", tags = {"电表统计数据"})
public class SummaryElectricDataController {
	@Autowired
	private ISummaryElectricDeviceDataHourService summaryElectricDeviceDataHourService;
	@Autowired
	private ISummaryElectricDeviceDataDayService summaryElectricDeviceDataDayService;
	@Autowired
	private ISummaryElectricDeviceDataMonthService summaryElectricDeviceDataMonthService;
	@Autowired
	private ISummaryElectricDeviceDataYearService summaryElectricDeviceDataYearService;

	@Resource
	private SummaryElectricDeviceDataHourMapper hourDao;
	@Resource
	private SummaryElectricDeviceDataDayMapper dayDao;
	@Resource
	private SummaryElectricDeviceDataMonthMapper monthDao;
	@Resource
	private SummaryElectricDeviceDataYearMapper yearDao;

	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取电表统计-时段数据分页列表", tags = "电表统计数据")
	@RequestMapping(value = "/listHour", method = RequestMethod.GET)
	public JSONObject queryPageListByHour(SummaryElectricDeviceDataHour data,
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

		Result<IPage<SummaryElectricDeviceDataHour>> result = new Result<IPage<SummaryElectricDeviceDataHour>>();
		QueryWrapper<SummaryElectricDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
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

		Page<SummaryElectricDeviceDataHour> page = new Page<SummaryElectricDeviceDataHour>(pageNo, pageSize);
		IPage<SummaryElectricDeviceDataHour> pageList = summaryElectricDeviceDataHourService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0d;
		double totalElectricWugong=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalElectricYougong = hourDao.getCountByYougongAndDayAndUser(user.getId(), year, month, day);
				totalElectricWugong = hourDao.getCountByWugongAndDayAndUser(user.getId(), year, month, day);
			} else {
				totalElectricYougong = hourDao.getCountByYougongAndDay(year, month, day);
				totalElectricWugong = hourDao.getCountByWugongAndDay(year, month, day);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取电表统计-日数据分页列表", tags = "电表统计数据")
	@RequestMapping(value = "/listDay", method = RequestMethod.GET)
	public JSONObject queryPageListByDay(SummaryElectricDeviceDataDay data,
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

		Result<IPage<SummaryElectricDeviceDataDay>> result = new Result<IPage<SummaryElectricDeviceDataDay>>();
		QueryWrapper<SummaryElectricDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
//			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));

		}
		queryWrapper.eq("summary_year",year);
		queryWrapper.eq("summary_month",month);
//			queryWrapper.eq("summary_day",day);
		Page<SummaryElectricDeviceDataDay> page = new Page<SummaryElectricDeviceDataDay>(pageNo, pageSize);
		IPage<SummaryElectricDeviceDataDay> pageList = summaryElectricDeviceDataDayService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0d;
		double totalElectricWugong=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalElectricYougong = dayDao.getCountByYougongAndMonthAndUser(user.getId(), year, month);
				totalElectricWugong = dayDao.getCountByWugongAndMonthAndUser(user.getId(), year, month);
			} else {
				totalElectricYougong = dayDao.getCountByYougongAndMonth(year, month);
				totalElectricWugong = dayDao.getCountByWugongAndMonth(year, month);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取电表统计-月数据分页列表", tags = "电表统计数据")
	@RequestMapping(value = "/listMonth", method = RequestMethod.GET)
	public JSONObject queryPageListByMonth(SummaryElectricDeviceDataMonth data,
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

		Result<IPage<SummaryElectricDeviceDataMonth>> result = new Result<IPage<SummaryElectricDeviceDataMonth>>();
		QueryWrapper<SummaryElectricDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));

		}
		queryWrapper.eq("summary_year",year);
//			queryWrapper.eq("summary_month",month);
//			queryWrapper.eq("summary_day",day);

		Page<SummaryElectricDeviceDataMonth> page = new Page<SummaryElectricDeviceDataMonth>(pageNo, pageSize);
		IPage<SummaryElectricDeviceDataMonth> pageList = summaryElectricDeviceDataMonthService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0d;
		double totalElectricWugong=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalElectricYougong = monthDao.getCountByYougongAndYearAndUser(user.getId(), year);
				totalElectricWugong = monthDao.getCountByWugongAndYearAndUser(user.getId(), year);
			} else {
				totalElectricYougong = monthDao.getCountByYougongAndYear(year);
				totalElectricWugong = monthDao.getCountByWugongAndYear(year);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}

	@ApiOperation(value = "获取电表统计-年数据分页列表", tags = "电表统计数据")
	@RequestMapping(value = "/listYear", method = RequestMethod.GET)
	public JSONObject queryPageListByYear(SummaryElectricDeviceDataYear data,
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

		Result<IPage<SummaryElectricDeviceDataYear>> result = new Result<IPage<SummaryElectricDeviceDataYear>>();
		QueryWrapper<SummaryElectricDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		//判断传入的时间
		if(StringUtils.isNotNullOrEmpty(startTime)){
			day=Integer.valueOf(DateUtil.getDay(DateUtil.parseDate(startTime)));
			month=Integer.valueOf(DateUtil.getMonth(DateUtil.parseDate(startTime)));
			year=Integer.valueOf(DateUtil.getYear(DateUtil.parseDate(startTime)));

		}
//			queryWrapper.eq("summary_year",year);
//			queryWrapper.eq("summary_month",month);
//			queryWrapper.eq("summary_day",day);

		Page<SummaryElectricDeviceDataYear> page = new Page<SummaryElectricDeviceDataYear>(pageNo, pageSize);
		IPage<SummaryElectricDeviceDataYear> pageList = summaryElectricDeviceDataYearService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalElectricYougong=0d;
		double totalElectricWugong=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalElectricYougong = yearDao.getCountByYougongAndTotalAndUser(user.getId());
				totalElectricWugong = yearDao.getCountByWugongAndTotalAndUser(user.getId());
			} else {
				totalElectricYougong = yearDao.getCountByYougongAndTotal();
				totalElectricWugong = yearDao.getCountByWugongAndTotal();
			}
		}

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalElectricYougong",totalElectricYougong); //总耗电-有功
		joTotalData.put("totalElectricWugong",totalElectricWugong); //总耗电-无功
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}


	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询电表统计-时段详情", tags = "电表统计数据")
	@RequestMapping(value = "/queryByIdAndHour", method = RequestMethod.GET)
	public Result<SummaryElectricDeviceDataHour> queryByIdAndHour(@RequestParam(name="id",required=true) String id) {
		Result<SummaryElectricDeviceDataHour> result = new Result<SummaryElectricDeviceDataHour>();
		SummaryElectricDeviceDataHour sysrole = summaryElectricDeviceDataHourService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询电表统计-日详情", tags = "电表统计数据")
	@RequestMapping(value = "/queryByIdAndDay", method = RequestMethod.GET)
	public Result<SummaryElectricDeviceDataDay> queryByIdAndDay(@RequestParam(name="id",required=true) String id) {
		Result<SummaryElectricDeviceDataDay> result = new Result<SummaryElectricDeviceDataDay>();
		SummaryElectricDeviceDataDay sysrole = summaryElectricDeviceDataDayService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询电表统计-月详情", tags = "电表统计数据")
	@RequestMapping(value = "/queryByIdAndMonth", method = RequestMethod.GET)
	public Result<SummaryElectricDeviceDataMonth> queryByIdAndMonth(@RequestParam(name="id",required=true) String id) {
		Result<SummaryElectricDeviceDataMonth> result = new Result<SummaryElectricDeviceDataMonth>();
		SummaryElectricDeviceDataMonth sysrole = summaryElectricDeviceDataMonthService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询电表统计-年详情", tags = "电表统计数据")
	@RequestMapping(value = "/queryByIdAndYear", method = RequestMethod.GET)
	public Result<SummaryElectricDeviceDataYear> queryByIdAndYear(@RequestParam(name="id",required=true) String id) {
		Result<SummaryElectricDeviceDataYear> result = new Result<SummaryElectricDeviceDataYear>();
		SummaryElectricDeviceDataYear sysrole = summaryElectricDeviceDataYearService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 导出excel
	 * @param request
	 */
	@ApiOperation(value = "导出电能统计-时段详情", tags = "电表统计数据")
	@RequestMapping(value = "/exportXlsByHour", method = RequestMethod.POST)
	public ModelAndView exportXlsByHour(SummaryElectricDeviceDataHour sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryElectricDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryElectricDeviceDataHour> pageList = summaryElectricDeviceDataHourService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"电表统计数据");
		mv.addObject(NormalExcelConstants.CLASS, SummaryElectricDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("电表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出电能统计-日详情", tags = "电表统计数据")
	@RequestMapping(value = "/exportXlsByDay", method = RequestMethod.POST)
	public ModelAndView exportXlsByDay(SummaryElectricDeviceDataDay sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryElectricDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryElectricDeviceDataDay> pageList = summaryElectricDeviceDataDayService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"电表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryElectricDeviceDataDay.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("电表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出电能统计-月详情", tags = "电表统计数据")
	@RequestMapping(value = "/exportXlsByMonth", method = RequestMethod.POST)
	public ModelAndView exportXlsByMonth(SummaryElectricDeviceDataMonth sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryElectricDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryElectricDeviceDataMonth> pageList = summaryElectricDeviceDataMonthService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"电表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryElectricDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("电表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}

	@ApiOperation(value = "导出电能统计-年详情", tags = "电表统计数据")
	@RequestMapping(value = "/exportXlsByYear", method = RequestMethod.POST)
	public ModelAndView exportXlsByDay(SummaryElectricDeviceDataYear sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryElectricDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryElectricDeviceDataYear> pageList = summaryElectricDeviceDataYearService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"电表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryElectricDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("电表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
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
