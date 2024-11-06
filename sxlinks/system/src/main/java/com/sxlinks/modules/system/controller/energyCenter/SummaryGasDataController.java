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
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataDay;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataHour;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataMonth;
import com.sxlinks.modules.system.entity.energyCenter.SummaryGasDeviceDataYear;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryGasDeviceDataDayMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryGasDeviceDataHourMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryGasDeviceDataMonthMapper;
import com.sxlinks.modules.system.mapper.energyCenter.SummaryGasDeviceDataYearMapper;
import com.sxlinks.modules.system.service.energyCenter.ISummaryGasDeviceDataDayService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryGasDeviceDataHourService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryGasDeviceDataMonthService;
import com.sxlinks.modules.system.service.energyCenter.ISummaryGasDeviceDataYearService;
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
 * 气表统计数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/energyCenter/summaryGasData")
@Slf4j
@Api(value = "能源中心", tags = {"气表统计数据"})
public class SummaryGasDataController {
	@Autowired
	private ISummaryGasDeviceDataHourService summaryGasDeviceDataHourService;
	@Autowired
	private ISummaryGasDeviceDataDayService summaryGasDeviceDataDayService;
	@Autowired
	private ISummaryGasDeviceDataMonthService summaryGasDeviceDataMonthService;
	@Autowired
	private ISummaryGasDeviceDataYearService summaryGasDeviceDataYearService;

	@Resource
	private SummaryGasDeviceDataHourMapper hourDao;
	@Resource
	private SummaryGasDeviceDataDayMapper dayDao;
	@Resource
	private SummaryGasDeviceDataMonthMapper monthDao;
	@Resource
	private SummaryGasDeviceDataYearMapper yearDao;
	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取气表统计-时段数据分页列表", tags = "气表统计数据")
	@RequestMapping(value = "/listHour", method = RequestMethod.GET)
	public JSONObject queryPageListByHour(SummaryGasDeviceDataHour data,
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

		Result<IPage<SummaryGasDeviceDataHour>> result = new Result<IPage<SummaryGasDeviceDataHour>>();
		QueryWrapper<SummaryGasDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
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

		Page<SummaryGasDeviceDataHour> page = new Page<SummaryGasDeviceDataHour>(pageNo, pageSize);
		IPage<SummaryGasDeviceDataHour> pageList = summaryGasDeviceDataHourService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalGas=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalGas = hourDao.getCountByPowerAndDayAndUser(user.getId(), year, month, day);
			} else {
				totalGas = hourDao.getCountByPowerAndDay(year, month, day);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalGas",totalGas); //总耗气
		jo.put("totalData",joTotalData);
		return jo;
		//return result;

	}

	@ApiOperation(value = "获取气表统计-日数据分页列表", tags = "气表统计数据")
	@RequestMapping(value = "/listDay", method = RequestMethod.GET)
	public JSONObject queryPageListByDay(SummaryGasDeviceDataDay data,
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

		Result<IPage<SummaryGasDeviceDataDay>> result = new Result<IPage<SummaryGasDeviceDataDay>>();
		QueryWrapper<SummaryGasDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
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
		Page<SummaryGasDeviceDataDay> page = new Page<SummaryGasDeviceDataDay>(pageNo, pageSize);
		IPage<SummaryGasDeviceDataDay> pageList = summaryGasDeviceDataDayService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalGas=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalGas = dayDao.getCountByPowerAndMonthAndUser(user.getId(), year, month);
			} else {
				totalGas = dayDao.getCountByPowerAndMonth(year, month);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalGas",totalGas); //总耗气
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取气表统计-月数据分页列表", tags = "气表统计数据")
	@RequestMapping(value = "/listMonth", method = RequestMethod.GET)
	public JSONObject queryPageListByMonth(SummaryGasDeviceDataMonth data,
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

		Result<IPage<SummaryGasDeviceDataMonth>> result = new Result<IPage<SummaryGasDeviceDataMonth>>();
		QueryWrapper<SummaryGasDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
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

		Page<SummaryGasDeviceDataMonth> page = new Page<SummaryGasDeviceDataMonth>(pageNo, pageSize);
		IPage<SummaryGasDeviceDataMonth> pageList = summaryGasDeviceDataMonthService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalGas=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalGas = monthDao.getCountByPowerAndYearAndUser(user.getId(), year);
			} else {
				totalGas = monthDao.getCountByPowerAndYear(year);
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalGas",totalGas); //总耗气
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}
	@ApiOperation(value = "获取气表统计-年数据分页列表", tags = "气表统计数据")
	@RequestMapping(value = "/listYear", method = RequestMethod.GET)
	public JSONObject queryPageListByYear(SummaryGasDeviceDataYear data,
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

		Result<IPage<SummaryGasDeviceDataYear>> result = new Result<IPage<SummaryGasDeviceDataYear>>();
		QueryWrapper<SummaryGasDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
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

		Page<SummaryGasDeviceDataYear> page = new Page<SummaryGasDeviceDataYear>(pageNo, pageSize);
		IPage<SummaryGasDeviceDataYear> pageList = summaryGasDeviceDataYearService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		double totalGas=0d;
		if(pageList.getRecords().size()>0) { //存在记录，才会去查询总数
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) {
				totalGas = yearDao.getCountByPowerAndTotalAndUser(user.getId());
			} else {
				totalGas = yearDao.getCountByPowerAndTotal();
			}
		}
		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));
		JSONObject joTotalData=new JSONObject();
		joTotalData.put("totalGas",totalGas); //总耗气
		jo.put("totalData",joTotalData);
		return jo;
		//return result;
	}

	@ApiOperation(value = "根据id查询水表统计-时段详情", tags = "气表统计数据")
	@RequestMapping(value = "/queryByIdAndHour", method = RequestMethod.GET)
	public Result<SummaryGasDeviceDataHour> queryByIdAndHour(@RequestParam(name="id",required=true) String id) {
		Result<SummaryGasDeviceDataHour> result = new Result<SummaryGasDeviceDataHour>();
		SummaryGasDeviceDataHour sysrole = summaryGasDeviceDataHourService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询水表统计-日段详情", tags = "气表统计数据")
	@RequestMapping(value = "/queryByIdAndDay", method = RequestMethod.GET)
	public Result<SummaryGasDeviceDataDay> queryByIdAndDay(@RequestParam(name="id",required=true) String id) {
		Result<SummaryGasDeviceDataDay> result = new Result<SummaryGasDeviceDataDay>();
		SummaryGasDeviceDataDay sysrole = summaryGasDeviceDataDayService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "根据id查询水表统计-月详情", tags = "气表统计数据")
	@RequestMapping(value = "/queryByIdAndMonth", method = RequestMethod.GET)
	public Result<SummaryGasDeviceDataMonth> queryByIdAndMonth(@RequestParam(name="id",required=true) String id) {
		Result<SummaryGasDeviceDataMonth> result = new Result<SummaryGasDeviceDataMonth>();
		SummaryGasDeviceDataMonth sysrole = summaryGasDeviceDataMonthService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "根据id查询水表统计-年详情", tags = "气表统计数据")
	@RequestMapping(value = "/queryByIdAndYear", method = RequestMethod.GET)
	public Result<SummaryGasDeviceDataYear> queryByIdAndYear(@RequestParam(name="id",required=true) String id) {
		Result<SummaryGasDeviceDataYear> result = new Result<SummaryGasDeviceDataYear>();
		SummaryGasDeviceDataYear sysrole = summaryGasDeviceDataYearService.getById(id);
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
	@ApiOperation(value = "导出气表统计-时段详情", tags = "气表统计数据")
	@RequestMapping(value = "/exportXlsByHour", method = RequestMethod.POST)
	public ModelAndView exportXlsByHour(SummaryGasDeviceDataHour sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryGasDeviceDataHour> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryGasDeviceDataHour> pageList = summaryGasDeviceDataHourService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"气表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryGasDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("气表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出气表统计-日详情", tags = "气表统计数据")
	@RequestMapping(value = "/exportXlsByDay", method = RequestMethod.POST)
	public ModelAndView exportXlsByDay(SummaryGasDeviceDataDay sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryGasDeviceDataDay> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryGasDeviceDataDay> pageList = summaryGasDeviceDataDayService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"气表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryGasDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("气表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出气表统计-月详情", tags = "气表统计数据")
	@RequestMapping(value = "/exportXlsByMonth", method = RequestMethod.POST)
	public ModelAndView exportXlsByMonth(SummaryGasDeviceDataMonth sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryGasDeviceDataMonth> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryGasDeviceDataMonth> pageList = summaryGasDeviceDataMonthService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"气表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryGasDeviceDataMonth.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("气表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}
	@ApiOperation(value = "导出气表统计-年详情", tags = "气表统计数据")
	@RequestMapping(value = "/exportXlsByYear", method = RequestMethod.POST)
	public ModelAndView exportXlsByYear(SummaryGasDeviceDataYear sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<SummaryGasDeviceDataYear> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<SummaryGasDeviceDataYear> pageList = summaryGasDeviceDataYearService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"气表统计数据列表");
		mv.addObject(NormalExcelConstants.CLASS, SummaryGasDeviceDataHour.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("气表统计数据列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return summaryGasDeviceDataService.importExcelCheckRoleCode(file, params);
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
