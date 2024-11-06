package com.sxlinks.modules.api.controller.miniProgram.ruleEngine;


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
import com.sxlinks.modules.system.entity.productCenter.DeviceFunction;
import com.sxlinks.modules.system.entity.productCenter.DeviceProperty;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.ruleEngine.ConditionShell;
import com.sxlinks.modules.system.entity.ruleEngine.ConditionShellItem;
import com.sxlinks.modules.system.service.productCenter.*;
import com.sxlinks.modules.system.service.ruleEngine.IConditionShellItemService;
import com.sxlinks.modules.system.service.ruleEngine.IConditionShellService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 条件联动表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/ruleEngine/condition")
@Slf4j
@Api(value = "规则引擎", tags = {"条件联动"})
public class MiniConditionController {
	@Autowired
	private IConditionShellService conditionShellService;
	@Autowired
	private IConditionShellItemService conditionShellItemService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDeviceFunctionService deviceFunctionService;
	@Autowired
	private IDevicePropertyService devicePropertyService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductPropertyService productPropertyService;
	@Resource
	private BaseCommonService baseCommonService;
	/**
	 * 分页列表查询
	 * @param conditionShell
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取联动分页列表", tags = "条件联动")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<ConditionShell>> queryPageList(ConditionShell conditionShell,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<ConditionShell>> result = new Result<IPage<ConditionShell>>();
		QueryWrapper<ConditionShell> queryWrapper = QueryGenerator.initQueryWrapper(conditionShell, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<ConditionShell> page = new Page<>(pageNo, pageSize);
		IPage<ConditionShell> pageList = conditionShellService.page(page, queryWrapper);
		trans(pageList.getRecords());
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取联动条件设置数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}
	@ApiOperation(value = "获取子项分页列表", tags = "条件联动")
	@RequestMapping(value = "/listItem", method = RequestMethod.GET)
	public Result<IPage<ConditionShellItem>> listItem(ConditionShellItem item,
											 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											 HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<ConditionShellItem>> result = new Result<IPage<ConditionShellItem>>();
		QueryWrapper<ConditionShellItem> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<ConditionShellItem> page = new Page<ConditionShellItem>(pageNo, pageSize);
		IPage<ConditionShellItem> pageList = conditionShellItemService.page(page, queryWrapper);
		transItem(pageList.getRecords());
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @param o
	 * @return
	 */
	@ApiOperation(value = "添加联动", tags = "条件联动")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<ConditionShell> add(@RequestBody ConditionShell o) {

		Result<ConditionShell> result = new Result<ConditionShell>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			Device device=deviceService.getById(o.getSourceDeviceId());
			o.setSourceDeviceId(device.getId());
			o.setSourceDeviceName(device.getDeviceName());
			o.setSourceDeviceCode(device.getDeviceCode());
			o.setCreateBy(user.getId());
			o.setCreateTime(new Date());
			conditionShellService.save(o);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加子项信息", tags = "条件联动")
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<ConditionShellItem> addItem(@RequestBody ConditionShellItem item) {
		Result<ConditionShellItem> result = new Result<ConditionShellItem>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			ConditionShell cs=conditionShellService.getById(item.getConditionShellId());
			Device device=deviceService.getById(cs.getSourceDeviceId());
			item.setDeviceId(device.getId());
			item.setDeviceName(device.getDeviceName());
			item.setDeviceCode(device.getDeviceCode());
			Product product=productService.getOne(new QueryWrapper<Product>().eq("product_code",device.getProductCode())); //产品
			item.setProductId(product.getId());
			item.setProductName(product.getProductName());
			item.setProductCode(product.getProductCode());
			DeviceProperty sourceProperty=devicePropertyService.getById(item.getPropertyId()); //源指定属性
			item.setPropertyName(sourceProperty.getName());
			item.setPropertyCode(sourceProperty.getIdentifier());
			Device targetDevice=deviceService.getById(item.getTargetDeviceId()); //目标设备
			item.setTargetDeviceId(targetDevice.getId());
			item.setTargetDeviceName(targetDevice.getDeviceName());
			item.setTargetDeviceCode(targetDevice.getDeviceCode());
			DeviceProperty tartgetProperty=devicePropertyService.getById(item.getTargetPropertyId()); //目标属性
			item.setTargetPropertyName(tartgetProperty.getName());
			item.setTargetPropertyCode(tartgetProperty.getIdentifier());

			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			conditionShellItemService.save(item);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  编辑
	 * @param conditionShell
	 * @return
	 */
	@ApiOperation(value = "编辑联动", tags = "条件联动")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<ConditionShell> edit(@RequestBody ConditionShell conditionShell) {
		Result<ConditionShell> result = new Result<ConditionShell>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		ConditionShell sysrole = conditionShellService.getById(conditionShell.getId());
		Device device=deviceService.getById(conditionShell.getSourceDeviceId());
		conditionShell.setSourceDeviceId(device.getId());
		conditionShell.setSourceDeviceName(device.getDeviceName());
		conditionShell.setSourceDeviceCode(device.getDeviceCode());
		conditionShell.setModifyBy(user.getId());
		conditionShell.setModifyTime(new Date());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			conditionShell.setModifyTime(new Date());
			boolean ok = conditionShellService.updateById(conditionShell);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改子项信息", tags = "条件联动")
	@RequestMapping(value = "/editItem", method = RequestMethod.PUT)
	public Result<ConditionShellItem> editItem(@RequestBody ConditionShellItem item) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<ConditionShellItem> result = new Result<ConditionShellItem>();
		ConditionShellItem sysrole = conditionShellItemService.getById(item.getId());

		ConditionShell cs=conditionShellService.getById(item.getConditionShellId());
		Device device=deviceService.getById(cs.getSourceDeviceId());
		item.setDeviceId(device.getId());
		item.setDeviceName(device.getDeviceName());
		item.setDeviceCode(device.getDeviceCode());
		Product product=productService.getOne(new QueryWrapper<Product>().eq("product_code",device.getProductCode())) ;//产品
		item.setProductId(product.getId());
		item.setProductName(product.getProductName());
		item.setProductCode(product.getProductCode());
		DeviceProperty sourceProperty=devicePropertyService.getById(item.getPropertyId()); //源指定属性
		item.setPropertyName(sourceProperty.getName());
		item.setPropertyCode(sourceProperty.getIdentifier());
		Device targetDevice=deviceService.getById(item.getTargetDeviceId()); //目标设备
		item.setTargetDeviceId(targetDevice.getId());
		item.setTargetDeviceName(targetDevice.getDeviceName());
		item.setTargetDeviceCode(targetDevice.getDeviceCode());
		DeviceProperty tartgetProperty=devicePropertyService.getById(item.getTargetPropertyId()); //目标属性
		item.setTargetPropertyName(tartgetProperty.getName());
		item.setTargetPropertyCode(tartgetProperty.getIdentifier());

		item.setModifyBy(user.getId());
		item.setModifyTime(new Date());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = conditionShellItemService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除子项信息", tags = "条件联动")
	@RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
	public Result<?> deleteItem(@RequestParam(name="propertyId",required=true) String itemId) {
		conditionShellItemService.removeById(itemId);
		return Result.ok("删除成功");
	}
	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除联动", tags = "条件联动")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		conditionShellService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除联动", tags = "条件联动")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<ConditionShell> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<ConditionShell> result = new Result<ConditionShell>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中条件联动！");
		}else {
			conditionShellService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除条件联动成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询联动", tags = "条件联动")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<ConditionShell> queryById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<ConditionShell> result = new Result<ConditionShell>();
		ConditionShell sysrole = conditionShellService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有联动", tags = "条件联动")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<ConditionShell>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<ConditionShell>> result = new Result<>();
		List<ConditionShell> list = conditionShellService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到条件联动信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验条件联动编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证条件联动编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			ConditionShell role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = conditionShellService.getById(id);
			}
			ConditionShell newRole = conditionShellService.getOne(new QueryWrapper<ConditionShell>().lambda().eq(ConditionShell::getCode, code));
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
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(ConditionShell sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<ConditionShell> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<ConditionShell> pageList = conditionShellService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"条件联动列表");
		mv.addObject(NormalExcelConstants.CLASS,ConditionShell.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("条件联动列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return conditionShellService.importExcelCheckRoleCode(file, params);
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

	private void trans(List<ConditionShell> list) {
		// sourceDeviceName字段补充
		if (Objects.nonNull(list) && !list.isEmpty()) {
			List<Long> deviceIds = list.stream().filter(o->Objects.nonNull(o.getSourceDeviceId()))
					.map(ConditionShell::getSourceDeviceId).collect(Collectors.toList());
			if (!deviceIds.isEmpty()) {
				List<Device> deviceList = deviceService.listByIds(deviceIds);
				if (Objects.nonNull(deviceList)) {
					Map<Long,String> idNameMap = deviceList.stream().collect(Collectors.toMap(Device::getId,Device::getDeviceName));
					for (ConditionShell conditionShell : list) {
						conditionShell.setSourceDeviceName(idNameMap.get(conditionShell.getSourceDeviceId()));
					}
				}
			}
		}
//		// typeName 字段补充
//		for (ConditionShell conditionShell : list) {
//			switch (conditionShell.getType()) {
//				case "1":
//					conditionShell.setTypeName("设备联动");
//					break;
//			}
//		}
	}

	private void transItem(List<ConditionShellItem> list) {
		if (Objects.nonNull(list) && !list.isEmpty()) {
			List<Long> functionIds = list.stream().filter(o->Objects.nonNull(o.getTargetFunctionId()))
					.map(ConditionShellItem::getTargetFunctionId).collect(Collectors.toList());
			if (!functionIds.isEmpty()) {
				List<DeviceFunction> functionList = deviceFunctionService.listByIds(functionIds);
				if (Objects.nonNull(functionList)) {
					Map<Long,String> idNameMap = functionList.stream().collect(Collectors.toMap(DeviceFunction::getId,DeviceFunction::getName));
					for (ConditionShellItem item : list) {
						item.setTargetFunctionName(idNameMap.get(item.getTargetFunctionId()));
					}
				}
			}
		}
	}
}
