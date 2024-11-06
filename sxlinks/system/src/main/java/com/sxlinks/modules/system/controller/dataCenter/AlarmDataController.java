package com.sxlinks.modules.system.controller.dataCenter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.dataCenter.AlarmRecord;
import com.sxlinks.modules.system.service.dataCenter.IAlarmRecordService;
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
 * 告警数据表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dataCenter/alarmData")
@Slf4j
@Api(value = "数据中心", tags = {"告警实时数据"})
public class AlarmDataController {
	@Autowired
	private IAlarmRecordService alarmRecordService;


	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取告警实时数据分页列表", tags = "告警实时数据")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<AlarmRecord>> queryPageList(AlarmRecord data,
													@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<AlarmRecord>> result = new Result<IPage<AlarmRecord>>();
		QueryWrapper<AlarmRecord> queryWrapper = QueryGenerator.initQueryWrapper(data, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<AlarmRecord> page = new Page<AlarmRecord>(pageNo, pageSize);
		IPage<AlarmRecord> pageList = alarmRecordService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		return result;
	}


	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除告警实时数据", tags = "告警实时数据")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		alarmRecordService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除告警实时数据", tags = "告警实时数据")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<AlarmRecord> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<AlarmRecord> result = new Result<AlarmRecord>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中告警数据！");
		}else {
			alarmRecordService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除告警数据成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询实时告警详情", tags = "告警实时数据")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<AlarmRecord> queryById(@RequestParam(name="id",required=true) String id) {
		Result<AlarmRecord> result = new Result<AlarmRecord>();
		AlarmRecord sysrole = alarmRecordService.getById(id);
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
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(AlarmRecord sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<AlarmRecord> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<AlarmRecord> pageList = alarmRecordService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"告警数据列表");
		mv.addObject(NormalExcelConstants.CLASS,AlarmRecord.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("告警数据列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return alarmRecordService.importExcelCheckRoleCode(file, params);
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
