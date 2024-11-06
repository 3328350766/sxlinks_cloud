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
import com.sxlinks.modules.system.entity.productCenter.DeviceProperty;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.ruleEngine.Alarm;
import com.sxlinks.modules.system.entity.ruleEngine.AlarmItem;
import com.sxlinks.modules.system.service.productCenter.IDevicePropertyService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import com.sxlinks.modules.system.service.ruleEngine.IAlarmItemService;
import com.sxlinks.modules.system.service.ruleEngine.IAlarmService;
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
 * 告警表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/ruleEngine/alarm")
@Slf4j
@Api(value = "规则引擎", tags = {"预警设置"})
public class MiniAlarmController {
	@Autowired
	private IAlarmService alarmService;
	@Autowired
	private IAlarmItemService alarmItemService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyService devicePropertyService;
	@Autowired
	private IProductService productService;
	@Resource
	private BaseCommonService baseCommonService;
	/**
	 * 分页列表查询
	 * @param alarm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取预警设置分页列表", tags = "预警设置")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Alarm>> queryPageList(Alarm alarm,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<Alarm>> result = new Result<IPage<Alarm>>();
		QueryWrapper<Alarm> queryWrapper = QueryGenerator.initQueryWrapper(alarm, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Alarm> page = new Page<Alarm>(pageNo, pageSize);
		IPage<Alarm> pageList = alarmService.custom(page,alarm);
		transAlarm(pageList.getRecords());
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取预警设置数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	@ApiOperation(value = "获取子项分页列表", tags = "预警设置")
	@RequestMapping(value = "/listItem", method = RequestMethod.GET)
	public Result<IPage<AlarmItem>> listItem(AlarmItem item,
															   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															   HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<AlarmItem>> result = new Result<IPage<AlarmItem>>();
		QueryWrapper<AlarmItem> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<AlarmItem> page = new Page<AlarmItem>(pageNo, pageSize);
		IPage<AlarmItem> pageList = alarmItemService.page(page, queryWrapper);
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
	@ApiOperation(value = "添加预警", tags = "预警设置")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Alarm> add(@RequestBody Alarm o) {

		Result<Alarm> result = new Result<Alarm>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			Device device=deviceService.getById(o.getSourceDeviceId());
			o.setSourceDeviceId(device.getId());
			o.setSourceDeviceName(device.getDeviceName());
			o.setSourceDeviceCode(device.getDeviceCode());
			o.setCreateBy(user.getId());
			o.setCreateTime(new Date());
			alarmService.save(o);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加子项信息", tags = "预警设置")
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<AlarmItem> addItem(@RequestBody AlarmItem item) {
		Result<AlarmItem> result = new Result<AlarmItem>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			Alarm alarm=alarmService.getById(item.getAlarmId());
			item.setAlarmCode(alarm.getCode());
			Device device=deviceService.getById(alarm.getSourceDeviceId());
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

			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			alarmItemService.save(item);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  编辑
	 * @param alarm
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "编辑预警", tags = "预警设置")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Alarm> edit(@RequestBody Alarm alarm) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Alarm> result = new Result<Alarm>();
		Alarm sysrole = alarmService.getByAlarmId(alarm.getId());
		Device device=deviceService.getById(alarm.getSourceDeviceId());
		alarm.setSourceDeviceId(device.getId());
		alarm.setSourceDeviceName(device.getDeviceName());
		alarm.setSourceDeviceCode(device.getDeviceCode());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			alarm.setModifyBy(user.getId());
			alarmService.updateById(alarm);
			//TODO 返回false说明什么？
			result.success("修改成功!");
		}

		return result;
	}
	@ApiOperation(value = "修改子项信息", tags = "预警设置")
	@RequestMapping(value = "/editItem", method = RequestMethod.PUT)
	public Result<AlarmItem> editItem(@RequestBody AlarmItem item) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<AlarmItem> result = new Result<AlarmItem>();
		AlarmItem sysrole = alarmItemService.getById(item.getId());

		Alarm alarm=alarmService.getById(item.getAlarmId());
		item.setAlarmCode(alarm.getCode());
		Device device=deviceService.getById(alarm.getSourceDeviceId());
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

		item.setModifyBy(user.getId());
		item.setModifyTime(new Date());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = alarmItemService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除子项信息", tags = "预警设置")
	@RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
	public Result<?> deleteItem(@RequestParam(name="propertyId",required=true) String itemId) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		alarmItemService.removeById(itemId);
		return Result.ok("删除成功");
	}

	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除预警", tags = "预警设置")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		alarmService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除预警", tags = "预警设置")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Alarm> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Alarm> result = new Result<Alarm>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中告警！");
		}else {
			alarmService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除告警成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询预警", tags = "预警设置")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Alarm> queryById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Alarm> result = new Result<Alarm>();
		Alarm sysrole = alarmService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据id查询预警子项", tags = "预警设置")
	@RequestMapping(value = "/queryItemById", method = RequestMethod.GET)
	public Result<AlarmItem> queryItemById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<AlarmItem> result = new Result<AlarmItem>();
		AlarmItem sysrole = alarmItemService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "获取所有预警设置", tags = "预警设置")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Alarm>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Alarm>> result = new Result<>();
		List<Alarm> list = alarmService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到告警信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验告警编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证告警编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			Alarm role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = alarmService.getById(id);
			}
			Alarm newRole = alarmService.getOne(new QueryWrapper<Alarm>().lambda().eq(Alarm::getCode, code));
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
	public ModelAndView exportXls(Alarm sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Alarm> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Alarm> pageList = alarmService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"告警列表");
		mv.addObject(NormalExcelConstants.CLASS,Alarm.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("告警列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return alarmService.importExcelCheckRoleCode(file, params);
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

	public void transItem(List<AlarmItem> itemList) {
		if (Objects.nonNull(itemList) && !itemList.isEmpty()) {
			List<Long> itemIds = itemList.stream().map(AlarmItem::getPropertyId).collect(Collectors.toList());
			if (!itemIds.isEmpty()) {
				List<DeviceProperty> findList = devicePropertyService.listByIds(itemIds);
				Map<Long,String> idNameMap = findList.stream().collect(Collectors.toMap(DeviceProperty::getId,DeviceProperty::getName));
				for (AlarmItem alarmItem : itemList) {
					alarmItem.setPropertyName(idNameMap.get(alarmItem.getPropertyId()));
				}
			}
		}
	}

	public void transItem(AlarmItem item) {
		if (Objects.nonNull(item) && Objects.nonNull(item.getPropertyId())) {
			DeviceProperty property = devicePropertyService.getById(item.getPropertyId());
			if (Objects.nonNull(property)) {
				item.setPropertyName(property.getName());
			}
		}
	}

	public void transAlarm(List<Alarm> list) {
		if (Objects.nonNull(list) && !list.isEmpty()) {
			List<Long> deviceIds = list.stream().filter(o->Objects.nonNull(o.getSourceDeviceId()))
					.map(Alarm::getSourceDeviceId).collect(Collectors.toList());
			if (!deviceIds.isEmpty()) {
				List<Device> findList = deviceService.listByIds(deviceIds);
				if (!findList.isEmpty()) {
					Map<Long,String> idNameMap = findList.stream().collect(Collectors.toMap(Device::getId,Device::getDeviceName));
					for (Alarm alarm : list) {
						alarm.setSourceDeviceName(idNameMap.get(alarm.getSourceDeviceId()));
					}
				}
			}
		}
	}
}
