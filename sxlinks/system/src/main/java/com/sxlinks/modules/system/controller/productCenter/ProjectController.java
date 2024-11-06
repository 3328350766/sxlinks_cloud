package com.sxlinks.modules.system.controller.productCenter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.productCenter.Project;
import com.sxlinks.modules.system.service.productCenter.IProjectService;
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
 * 项目表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/productCenter/project")
@Slf4j
@Api(value = "产品中心", tags = {"项目管理"})
public class ProjectController {
	@Autowired
	private IProjectService projectService;
	@Resource
	private BaseCommonService baseCommonService;

	/**
	 * 分页列表查询
	 * @param project
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取项目分页列表", tags = "项目管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Project>> queryPageList(Project project,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		Result<IPage<Project>> result = new Result<IPage<Project>>();
		//QueryWrapper<Project> queryWrapper = QueryGenerator.initQueryWrapper(project, req.getParameterMap());
		QueryWrapper<Project> queryWrapper = new QueryWrapper<Project>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		if(StringUtils.isNotNullOrEmpty(project.getCityName())){
			queryWrapper.like("city_name","%"+project.getCityName()+"%");
		}else{
			if(StringUtils.isNotNullOrEmpty(project.getProvinceName()))
				queryWrapper.like("province_name","%"+project.getProvinceName()+"%");
		}

		queryWrapper.orderByAsc("sort_no");  //根据序号升序排序
		Page<Project> page = new Page<Project>(pageNo, pageSize);
		IPage<Project> pageList = projectService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取项目数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	/**
	 *   添加
	 * @param project
	 * @return
	 */
	@ApiOperation(value = "新增项目", tags = "项目管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Project> add(@RequestBody Project project) {
		Result<Project> result = new Result<Project>();
		try {
			project.setCreateTime(new Date());
			projectService.save(project);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 *  编辑
	 * @param project
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "编辑项目", tags = "项目管理")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Project> edit(@RequestBody Project project) {
		Result<Project> result = new Result<Project>();
		Project sysproject = projectService.getById(project.getId());
		if(sysproject==null) {
			result.error500("未找到对应实体");
		}else {
			project.setModifyTime(new Date());
			boolean ok = projectService.updateById(project);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除项目", tags = "项目管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		projectService.removeById(id);
		return Result.ok("删除项目成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除项目", tags = "项目管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Project> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Project> result = new Result<Project>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中项目！");
		}else {
			projectService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除项目成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询项目", tags = "项目管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Project> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Project> result = new Result<Project>();
		Project sysproject = projectService.getById(id);
		if(sysproject==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysproject);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 启用
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "启用", tags = "项目管理")
	@RequestMapping(value = "/enable", method = RequestMethod.GET)
	public Result<Project> enable(@RequestParam(name="id",required=true) String id) {
		Result<Project> result = new Result<Project>();
		Project o = projectService.getById(id);
		if(o==null) {
			result.error500("未找到对应实体");
		}else {
			o.setState("1");
			projectService.updateById(o);
			result.setResult(o);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 启用
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "禁用", tags = "项目管理")
	@RequestMapping(value = "/disable", method = RequestMethod.GET)
	public Result<Project> disable(@RequestParam(name="id",required=true) String id) {
		Result<Project> result = new Result<Project>();
		Project o = projectService.getById(id);
		if(o==null) {
			result.error500("未找到对应实体");
		}else {
			o.setState("0");
			projectService.updateById(o);
			result.setResult(o);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Project>> queryall() {
		Result<List<Project>> result = new Result<>();
		List<Project> list = projectService.list(new QueryWrapper<Project>().orderByAsc("sort_no"));  //根据序号升序排序;
		if(list==null||list.size()<=0) {
			result.error500("未找到项目信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验项目编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证项目编码是否唯一---id:"+id+"--projectCode:"+code);
		try {
			Project project = null;
			if(oConvertUtils.isNotEmpty(id)) {
				project = projectService.getById(id);
			}
			Project newRole = projectService.getOne(new QueryWrapper<Project>().lambda().eq(Project::getCode, code));
			if(newRole!=null) {
				//如果根据传入的projectCode查询到信息了，那么就需要做校验了。
				if(project==null) {
					//project为空=>新增模式=>只要projectCode存在则返回false
					result.setSuccess(false);
					result.setMessage("编码已存在");
					return result;
				}else if(!id.equals(newRole.getId())) {
					//否则=>编辑模式=>判断两者ID是否一致-
					result.setSuccess(false);
					result.setMessage("编码已存在");
					return result;
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(false);
			result.setMessage(e.getMessage());
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	/**
	 * 导出excel
	 * @param request
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Project sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Project> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Project> pageList = projectService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"项目列表");
		mv.addObject(NormalExcelConstants.CLASS,Project.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("项目列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return projectService.importExcelCheckRoleCode(file, params);
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
