//package com.sxlinks.modules.system.controller.productCenter;
//
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.sxlinks.common.api.vo.Result;
//import com.sxlinks.common.system.query.QueryGenerator;
//import com.sxlinks.common.system.vo.LoginUser;
//import com.sxlinks.common.util.oConvertUtils;
//import com.sxlinks.modules.system.entity.productCenter.DeviceCamera;
//import com.sxlinks.modules.system.service.productCenter.IDeviceCameraService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.jeecgframework.poi.excel.def.NormalExcelConstants;
//import org.jeecgframework.poi.excel.entity.ExportParams;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>
// * 角色表 前端控制器
// * </p>
// *
// * @Author baba
// * @since 2022-06-02
// */
//@RestController
//@RequestMapping("/productCenter/camera")
//@Slf4j
//@Api(value = "产品中心", tags = {"监控管理"})
//public class DeviceCameraController {
//	@Autowired
//	private IDeviceCameraService deviceCameraService;
//
//
//
//	/**
//	 * 分页列表查询
//	 * @param deviceCamera
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@ApiOperation(value = "获取监控分页列表", tags = "监控管理")
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public Result<IPage<DeviceCamera>> queryPageList(DeviceCamera deviceCamera,
//												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//												HttpServletRequest req) {
//		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//		Result<IPage<DeviceCamera>> result = new Result<IPage<DeviceCamera>>();
//		QueryWrapper<DeviceCamera> queryWrapper = QueryGenerator.initQueryWrapper(deviceCamera, req.getParameterMap());
//		//非管理员情况下，只能查看当前用户数据
//		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
//		Page<DeviceCamera> page = new Page<DeviceCamera>(pageNo, pageSize);
//		IPage<DeviceCamera> pageList = deviceCameraService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	 *   添加
//	 * @param role
//	 * @return
//	 */
//	@ApiOperation(value = "添加监控信息", tags = "监控管理")
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	//@RequiresRoles({"admin"})
//	public Result<DeviceCamera> add(@RequestBody DeviceCamera role) {
//		Result<DeviceCamera> result = new Result<DeviceCamera>();
//		try {
//			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//			role.setCreateBy(user.getId());
//			role.setCreateTime(new Date());
//			deviceCameraService.save(role);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
//
//
//	/**
//	 *  编辑
//	 * @param deviceCamera
//	 * @return
//	 */
//	//@RequiresRoles({"admin"})
//	@ApiOperation(value = "修改监控信息", tags = "监控管理")
//	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
//	public Result<DeviceCamera> edit(@RequestBody DeviceCamera deviceCamera) {
//		Result<DeviceCamera> result = new Result<DeviceCamera>();
//		DeviceCamera sysrole = deviceCameraService.getById(deviceCamera.getId());
//		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//		sysrole.setModifyBy(user.getId());
//		sysrole.setModifyTime(new Date());
//		if(sysrole==null) {
//			result.error500("未找到对应实体");
//		}else {
//			//role.setModifyTime(new Date());
//			boolean ok = deviceCameraService.updateById(deviceCamera);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	 *   通过id删除
//	 * @param id
//	 * @return
//	 */
//	//@RequiresRoles({"admin"})
//	@ApiOperation(value = "删除监控信息", tags = "监控管理")
//	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
//		//deviceCameraService.removeById(id);
//		DeviceCamera p = deviceCameraService.getById(id);
//		p.setDelFlag(1); //软删除
//		deviceCameraService.saveOrUpdate(p);
//		return Result.ok("删除成功");
//	}
//
//	/**
//	 *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	//@RequiresRoles({"admin"})
//	@ApiOperation(value = "批量删除监控信息", tags = "监控管理")
//	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
//	public Result<DeviceCamera> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<DeviceCamera> result = new Result<DeviceCamera>();
//		if(oConvertUtils.isEmpty(ids)) {
//			result.error500("未选中角色！");
//		}else {
//			deviceCameraService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除角色成功!");
//		}
//		return result;
//	}
//
//	/**
//	 * 通过id查询
//	 * @param id
//	 * @return
//	 */
//	@ApiOperation(value = "通过id查询监控信息", tags = "监控管理")
//	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
//	public Result<DeviceCamera> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<DeviceCamera> result = new Result<DeviceCamera>();
//		DeviceCamera sysrole = deviceCameraService.getById(id);
//		if(sysrole==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(sysrole);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//
//	@ApiOperation(value = "查询所有监控信息", tags = "监控管理")
//	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
//	public Result<List<DeviceCamera>> queryall() {
//		Result<List<DeviceCamera>> result = new Result<>();
//		List<DeviceCamera> list = deviceCameraService.list();
//		if(list==null||list.size()<=0) {
//			result.error500("未找到监控信息");
//		}else {
//			result.setResult(list);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//	@ApiOperation(value = "查询所有在线监控", tags = "监控管理")
//	@RequestMapping(value = "/queryOnline", method = RequestMethod.GET)
//	public Result<List<DeviceCamera>> queryOnline() {
//		Result<List<DeviceCamera>> result = new Result<>();
//
//		List<DeviceCamera> list = deviceCameraService.list(new QueryWrapper<DeviceCamera>().eq("active_status","1"));
//		if(list==null||list.size()<=0) {
//			result.error500("未找到监控信息");
//		}else {
//			result.setResult(list);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//
//	@ApiOperation(value = "查询所有离线监控", tags = "监控管理")
//	@RequestMapping(value = "/queryOffline", method = RequestMethod.GET)
//	public Result<List<DeviceCamera>> queryOffline() {
//		Result<List<DeviceCamera>> result = new Result<>();
//		List<DeviceCamera> list = deviceCameraService.list(new QueryWrapper<DeviceCamera>().eq("active_status","0"));
//		if(list==null||list.size()<=0) {
//			result.error500("未找到监控信息");
//		}else {
//			result.setResult(list);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//
//
//	/**
//	 * 校验监控编码唯一
//	 */
//	@ApiOperation(value = "检测监控编码唯一", tags = "监控管理")
//	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
//	public Result<Boolean> checkCode(String id,String code) {
//		Result<Boolean> result = new Result<>();
//		result.setResult(true);//如果此参数为false则程序发生异常
//		log.info("--验证监控编码是否唯一---id:"+id+"--roleCode:"+code);
//		try {
//			DeviceCamera role = null;
//			if(oConvertUtils.isNotEmpty(id)) {
//				role = deviceCameraService.getById(id);
//			}
//			DeviceCamera newRole = deviceCameraService.getOne(new QueryWrapper<DeviceCamera>().lambda().eq(DeviceCamera::getCode, code));
//			if(newRole!=null) {
//				//如果根据传入的roleCode查询到信息了，那么就需要做校验了。
//				if(role==null) {
//					//role为空=>新增模式=>只要roleCode存在则返回false
//					result.setSuccess(false);
//					result.setMessage("编码已存在");
//					return result;
//				}else if(!id.equals(newRole.getId())) {
//					//否则=>编辑模式=>判断两者ID是否一致-
//					result.setSuccess(false);
//					result.setMessage("编码已存在");
//					return result;
//				}
//			}
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setResult(false);
//			result.setMessage(e.getMessage());
//			return result;
//		}
//		result.setSuccess(true);
//		return result;
//	}
//
//	/**
//	 * 导出excel
//	 * @param request
//	 */
//	//@ApiOperation(value = "导出excel监控信息", tags = "监控管理")
//	@RequestMapping(value = "/exportXls")
//	public ModelAndView exportXls(DeviceCamera sysRole, HttpServletRequest request) {
//		// Step.1 组装查询条件
//		QueryWrapper<DeviceCamera> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
//		//Step.2 AutoPoi 导出Excel
//		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//		List<DeviceCamera> pageList = deviceCameraService.list(queryWrapper);
//		//导出文件名称
//		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
//		mv.addObject(NormalExcelConstants.CLASS,DeviceCamera.class);
//		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("角色列表数据","导出人:"+user.getRealname(),"导出信息"));
//		mv.addObject(NormalExcelConstants.DATA_LIST,pageList);
//		return mv;
//	}
//
//	/**
//	 * 通过excel导入数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@ApiOperation(value = "导出excel监控信息", tags = "监控管理")
//	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//			MultipartFile file = entity.getValue();// 获取上传文件对象
//			ImportParams params = new ImportParams();
//			params.setTitleRows(2);
//			params.setHeadRows(1);
//			params.setNeedSave(true);
//			try {
//				//return deviceCameraService.importExcelCheckRoleCode(file, params);
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//				return Result.error("文件导入失败:" + e.getMessage());
//			} finally {
//				try {
//					file.getInputStream().close();
//				} catch (IOException e) {
//					log.error(e.getMessage(), e);
//				}
//			}
//		}
//		return Result.error("文件导入失败！");
//	}
//
//
//}
