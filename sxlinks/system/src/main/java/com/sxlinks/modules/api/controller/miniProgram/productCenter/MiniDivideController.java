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
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Divide;
import com.sxlinks.modules.system.entity.productCenter.DivideDeviceMap;
import com.sxlinks.modules.system.mapper.productCenter.DivideDeviceMapMapper;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IDivideDeviceMapService;
import com.sxlinks.modules.system.service.productCenter.IDivideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * <p>
 * 分组表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/productCenter/divide")
@Slf4j
@Api(value = "产品中心", tags = {"分组管理"})
public class MiniDivideController {
	@Autowired
	private IDivideService divideService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDivideDeviceMapService divideDeviceMapService;
	@Autowired
	private DivideDeviceMapMapper divideDeviceMapMapper;
	@Resource
	private BaseCommonService baseCommonService;
	/**
	 * 分页列表查询
	 * @param divide
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取分组分页列表", tags = "分组管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Divide>> queryPageList(Divide divide,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<Divide>> result = new Result<IPage<Divide>>();
		QueryWrapper<Divide> queryWrapper = QueryGenerator.initQueryWrapper(divide, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Divide> page = new Page<Divide>(pageNo, pageSize);
		IPage<Divide> pageList = divideService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取分组数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	/**
	 *   添加
	 * @param divide
	 * @return
	 */
	@ApiOperation(value = "添加分组", tags = "分组管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Divide> add(@RequestBody Divide divide) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Divide> result = new Result<Divide>();
		try {
			if(StringUtils.isEmpty(divide.getCode())){
				result.error500("操作失败,编码不能为空!");
			}else {
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("code", divide.getCode());
				Divide d = divideService.getOne(queryWrapper);
				if (d != null) {
					result.error500("操作失败,编码已经存在");
				} else {
					divide.setState("1"); //激活状态 0-未激活 1-已激活
					divide.setCreateBy(user.getId());
					divide.setCreateTime(new Date());
					divideService.save(divide);
					result.success("添加成功！");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 *   添加
	 * @return
	 */

	@ApiOperation(value = "绑定设备", tags = "分组管理")
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DivideDeviceMap> bind(@RequestParam(name="divideCode", defaultValue="")String divideCode,
							   @RequestParam(name="deviceCode", defaultValue="")String deviceCode) {
		Result<DivideDeviceMap> result = new Result<DivideDeviceMap>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			Map param=new HashMap();
			param.put("divide_code",divideCode);
			param.put("device_code",deviceCode);
			QueryWrapper<DivideDeviceMap> queryWrapper=new QueryWrapper<DivideDeviceMap>().allEq(param);
			DivideDeviceMap d = divideDeviceMapService.getOne(queryWrapper);
			if(d!=null){
				result.error500("操作失败,设备编码已经绑定到其它分组");
			}else{
				DivideDeviceMap o=new DivideDeviceMap();
				Divide divide=this.divideService.getOne(new QueryWrapper<Divide>().eq("code",divideCode));
				Device device=this.deviceService.getOne(new QueryWrapper<Device>().eq("device_code",deviceCode));
				o.setDeviceId(device.getId());
				o.setDivideId(divide.getId());
				o.setDivideCode(divide.getCode());
				o.setDeviceCode(device.getDeviceCode());
				o.setDivideCode(divideCode);
				o.setDeviceCode(deviceCode);
				o.setCreateBy(user.getId());
				o.setCreateTime(new Date());
				divideDeviceMapService.save(o);
				result.success("绑定成功！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "绑定设备根据Id", tags = "分组管理")
	@RequestMapping(value = "/bindById", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DivideDeviceMap> bindById(@RequestParam(name="divideId", defaultValue="")Long divideId,
										@RequestParam(name="deviceId", defaultValue="")Long deviceId) {
		Result<DivideDeviceMap> result = new Result<DivideDeviceMap>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			Map param=new HashMap();
			param.put("divide_id",divideId);
			param.put("device_id",deviceId);
			QueryWrapper<DivideDeviceMap> queryWrapper=new QueryWrapper<DivideDeviceMap>().allEq(param);
			DivideDeviceMap d = divideDeviceMapService.getOne(queryWrapper);
			if(d!=null){
				result.error500("操作失败,设备编码已经绑定到其它分组");
			}else{
				DivideDeviceMap o=new DivideDeviceMap();
				Divide divide=this.divideService.getById(divideId);
				Device device=this.deviceService.getById(deviceId);
				o.setDeviceId(device.getId());
				o.setDivideId(divide.getId());
				o.setDivideCode(divide.getCode());
				o.setDeviceCode(device.getDeviceCode());
				o.setCreateBy(user.getId());
				o.setCreateTime(new Date());
				divideDeviceMapService.save(o);
				result.success("绑定成功！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}


	/**
	 *   通过id删除
	 * @return
	 */
	@ApiOperation(value = "解绑设备", tags = "分组管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	public Result<?> unbind(@RequestParam(name="divideCode", defaultValue="")String divideCode,
							@RequestParam(name="deviceCode", defaultValue="")String deviceCode) {
		Result<Device> result = new Result<Device>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			QueryWrapper queryWrapper=new QueryWrapper();
			queryWrapper.eq("divide_code",divideCode);
			queryWrapper.eq("device_code",deviceCode); //设备编码查找
			DivideDeviceMap d = divideDeviceMapService.getOne(queryWrapper);
			if(d==null){
				result.error500("操作失败,设备编码没有绑定任何分组！");
			}else{
				divideDeviceMapService.removeById(d.getId());
				result.success("解绑成功！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "解绑设备根据id", tags = "分组管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/unbindById", method = RequestMethod.POST)
	public Result<?> unbindById(@RequestParam(name="divideId", defaultValue="")Long divideId,
							@RequestParam(name="deviceId", defaultValue="")Long deviceId) {
		Result<Device> result = new Result<Device>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			QueryWrapper queryWrapper=new QueryWrapper();
			queryWrapper.eq("divide_id",divideId);
			queryWrapper.eq("device_id",deviceId); //设备编码查找
			DivideDeviceMap d = divideDeviceMapService.getOne(queryWrapper);
			if(d==null){
				result.error500("操作失败,设备编码没有绑定分组！");
			}else{
				divideDeviceMapService.removeById(d.getId());
				result.success("解绑成功！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  编辑
	 * @param divide
	 * @return
	 */
	@ApiOperation(value = "编辑分组", tags = "分组管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Divide> edit(@RequestBody Divide divide) {
		Result<Divide> result = new Result<Divide>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Divide sysdivide = divideService.getById(divide.getId());
		divide.setModifyBy(user.getId());
		divide.setModifyTime(new Date());
		if(sysdivide==null) {
			result.error500("未找到对应实体");
		}else {
			divide.setModifyTime(new Date());
			boolean ok = divideService.updateById(divide);
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
	@ApiOperation(value = "删除分组", tags = "分组管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		divideService.removeById(id);
		return Result.ok("删除角色成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除分组", tags = "分组管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Divide> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Divide> result = new Result<Divide>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			divideService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除角色成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询分组", tags = "分组管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Divide> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Divide> result = new Result<Divide>();
		Divide sysdivide = divideService.getById(id);
		if(sysdivide==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysdivide);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有分组", tags = "分组管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Divide>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Divide>> result = new Result<>();
		List<Divide> list = divideService.list(new QueryWrapper<Divide>().eq("create_by",user.getId()));
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询此分组下已绑定的所有设备(根据分组编码)", tags = "分组管理")
	@RequestMapping(value = "/queryAllDeviceByDivideCode", method = RequestMethod.GET)
	public Result<List<Device>> queryAllDeviceByDivideCode(@RequestParam(name="divideCode",required=true) String divideCode) {
		Result<List<Device>> result = new Result<>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		List<Device> list = divideDeviceMapMapper.getDeviceListByDivideCode(divideCode);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询此分组下已绑定的所有设备(根据分组id)", tags = "分组管理")
	@RequestMapping(value = "/queryAllDeviceByDivideId", method = RequestMethod.GET)
	public Result<List<Device>> queryAllDeviceByDivideId(@RequestParam(name="divideId",required=true) String divideId) {
		Result<List<Device>> result = new Result<>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		List<Device> list = divideDeviceMapMapper.getDeviceListByDivideId(divideId);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
//	/**
//	 * 校验角色编码唯一
//	 */
//	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
//	public Result<Boolean> checkUsername(String id,String code) {
//		Result<Boolean> result = new Result<>();
//		result.setResult(true);//如果此参数为false则程序发生异常
//		log.info("--验证角色编码是否唯一---id:"+id+"--divideCode:"+code);
//		try {
//			Divide divide = null;
//			if(oConvertUtils.isNotEmpty(id)) {
//				divide = divideService.getById(id);
//			}
//			Divide newRole = divideService.getOne(new QueryWrapper<Divide>().lambda().eq(Divide::getPointCode, code));
//			if(newRole!=null) {
//				//如果根据传入的divideCode查询到信息了，那么就需要做校验了。
//				if(divide==null) {
//					//divide为空=>新增模式=>只要divideCode存在则返回false
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

	/**
	 * 导出excel
	 * @param request
	 */

	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Divide sysRole,HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<Divide> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Divide> pageList = divideService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,Divide.class);
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
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//return divideService.importExcelCheckRoleCode(file, params);
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
