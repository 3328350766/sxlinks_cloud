package com.sxlinks.modules.api.controller.miniProgram.productCenter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.productCenter.Location;
import com.sxlinks.modules.system.service.productCenter.ILocationService;
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
 * 角色表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/productCenter/location")
@Slf4j
@Api(value = "产品中心", tags = {"位置管理"})
public class MiniLocationController {
	@Autowired
	private ILocationService locationService;

	@Resource
	private BaseCommonService baseCommonService;

	/**
	 * 分页列表查询
	 * @param location
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取位置分页列表", tags = "位置管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Location>> queryPageList(Location location,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<Location>> result = new Result<IPage<Location>>();
		QueryWrapper<Location> queryWrapper = QueryGenerator.initQueryWrapper(location, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Location> page = new Page<Location>(pageNo, pageSize);
		IPage<Location> pageList = locationService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取地理数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	/**
	 *   添加
	 * @param role
	 * @return
	 */
	@ApiOperation(value = "添加位置信息", tags = "位置管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Location> add(@RequestBody Location role) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Location> result = new Result<Location>();
		try {
			role.setCreateTime(new Date());
			locationService.save(role);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}


	/**
	 *  编辑
	 * @param location
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "修改位置信息", tags = "位置管理")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Location> edit(@RequestBody Location location) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Location> result = new Result<Location>();
		Location sysrole = locationService.getById(location.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = locationService.updateById(location);
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
	@ApiOperation(value = "删除位置信息", tags = "位置管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		//locationService.removeById(id);
		Location p = locationService.getById(id);
		p.setDelFlag(1); //软删除
		locationService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除位置信息", tags = "位置管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Location> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Location> result = new Result<Location>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			locationService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除角色成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询位置信息", tags = "位置管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Location> queryById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Location> result = new Result<Location>();
		Location sysrole = locationService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有位置信息", tags = "位置管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Location>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Location>> result = new Result<>();
		List<Location> list = locationService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到位置信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有在线位置", tags = "位置管理")
	@RequestMapping(value = "/queryOnline", method = RequestMethod.GET)
	public Result<List<Location>> queryOnline() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Location>> result = new Result<>();

		List<Location> list = locationService.list(new QueryWrapper<Location>().eq("active_status","1"));
		if(list==null||list.size()<=0) {
			result.error500("未找到位置信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有离线位置", tags = "位置管理")
	@RequestMapping(value = "/queryOffline", method = RequestMethod.GET)
	public Result<List<Location>> queryOffline() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Location>> result = new Result<>();
		List<Location> list = locationService.list(new QueryWrapper<Location>().eq("active_status","0"));
		if(list==null||list.size()<=0) {
			result.error500("未找到位置信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}


	/**
	 * 校验位置编码唯一
	 */
	@ApiOperation(value = "检测位置编码唯一", tags = "位置管理")
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证位置编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			Location role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = locationService.getById(id);
			}
			Location newRole = locationService.getOne(new QueryWrapper<Location>().lambda().eq(Location::getCode, code));
			if(newRole!=null) {
				//role为空=>新增模式=>只要roleCode存在则返回false
				result.setSuccess(false);
				result.setMessage("编码已存在");
				return result;
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
	//@ApiOperation(value = "导出excel位置信息", tags = "位置管理")
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Location sysRole, HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<Location> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Location> pageList = locationService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,Location.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("角色列表数据","导出人:"+user.getRealname(),"导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
		return mv;
	}

	/**
	 * 通过excel导入数据
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "导出excel位置信息", tags = "位置管理")
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
				//return locationService.importExcelCheckRoleCode(file, params);
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
