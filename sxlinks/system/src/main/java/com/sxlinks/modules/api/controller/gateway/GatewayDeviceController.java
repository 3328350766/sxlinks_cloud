package com.sxlinks.modules.api.controller.gateway;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.domain.DataResult;
import com.sxlinks.common.domain.dto.request.device.PropertySetReqDto;
import com.sxlinks.common.domain.gateway.mq.MQSendMessageBo;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;
import com.sxlinks.modules.api.controller.gateway.vo.CloudDevice;
import com.sxlinks.modules.system.entity.productCenter.*;
import com.sxlinks.modules.system.service.productCenter.*;
import com.sxlinks.utils.IdGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 * 子设备信息由scada系统生成上传
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/gateway/api/device")
@Slf4j
@Api(value = "网关设备", tags = {"设备管理"})
public class GatewayDeviceController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyService devicePropertyService;
	@Autowired
	private IDeviceFunctionService deviceFunctionService;
	@Autowired
	private IModbusPointService modbusPointService;
	@Autowired
	private IStringPointService stringPointService;
	@Autowired
	private IJsonPointService jsonPointService;
	@Autowired
	RedisDaoImpl redisDao; //redis缓存

	/**
	 * 分页列表查询
	 * @param device
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取设备分页列表", tags = "设备管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<CloudDevice>> queryPageList(Device device,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		Result<IPage<CloudDevice>> result = new Result<IPage<CloudDevice>>();
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		queryWrapper.eq("del_flag",0); //正常状态
		queryWrapper.eq("node_type","SUB"); //子设备
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);

		//将功能和属性封装在产品列表中
		IPage<CloudDevice> pageList1=new Page<CloudDevice>();
		BeanUtils.copyProperties(pageList,pageList1);
		List<CloudDevice> lsNewData=new ArrayList();
		List<Device> lsDevice=pageList.getRecords();
		for(int i=0;i<lsDevice.size();i++){
			Device od=(Device)lsDevice.get(i);
			CloudDevice gv=new CloudDevice();
			BeanUtils.copyProperties(od,gv);
			gv.setLsProperty(devicePropertyService.list(new QueryWrapper<DeviceProperty>().eq("device_code",od.getDeviceCode()).eq("del_flag",0)));
			gv.setLsFunction(deviceFunctionService.list(new QueryWrapper<DeviceFunction>().eq("device_code",od.getDeviceCode()).eq("del_flag",0)));
			lsNewData.add(gv);
		}

		pageList1.setRecords(lsNewData);
		result.setSuccess(true);
		result.setResult(pageList1);
		return result;
	}

	@ApiOperation(value = "获取软网关的设备分页列表", tags = "设备管理")
	@RequestMapping(value = "/listBySoft", method = RequestMethod.GET)
	public Result<IPage<CloudDevice>> queryPageListBySoft(@RequestParam(name="deviceCode", defaultValue="") String deviceCode,
													@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													HttpServletRequest req) {
		Result<IPage<CloudDevice>> result = new Result<IPage<CloudDevice>>();
		//只解析软网关的deviceCode即可
		QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag",0); //正常状态
		queryWrapper.eq("enable_status",1); //激活状态
		queryWrapper.eq("node_type","SUB"); //子设备
		queryWrapper.eq("gw_dev_code",deviceCode);
		//queryWrapper.eq("protocol_type","dtu-modbus-rtu");
		//queryWrapper.or().eq("protocol_type","dtu-modbus-tcp");
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);

		//将功能和属性封装在产品列表中
		IPage<CloudDevice> pageList1=new Page<CloudDevice>();
		BeanUtils.copyProperties(pageList,pageList1);
		List<CloudDevice> lsNewData=new ArrayList();
		List<Device> lsDevice=pageList.getRecords();
		for(int i=0;i<lsDevice.size();i++){
			Device od=(Device)lsDevice.get(i);
			CloudDevice gv=new CloudDevice();
			BeanUtils.copyProperties(od,gv);
			gv.setLsProperty(devicePropertyService.list(new QueryWrapper<DeviceProperty>().eq("device_code",od.getDeviceCode()).eq("del_flag",0)));
			gv.setLsPropertyModbusPoint(modbusPointService.list(new QueryWrapper<ModbusPoint>().eq("device_code",od.getDeviceCode()).eq("state","1"))); //modbus点位列表
			gv.setLsPropertyStringPoint(stringPointService.list(new QueryWrapper<StringPoint>().eq("device_code",od.getDeviceCode()).eq("state","1"))); //string点位列表
			gv.setLsPropertyJsonPoint(jsonPointService.list(new QueryWrapper<JsonPoint>().eq("device_code",od.getDeviceCode()).eq("state","1"))); //json点位列表
			//gv.setLsPropertyCustomPoint();
			gv.setLsFunction(deviceFunctionService.list(new QueryWrapper<DeviceFunction>().eq("device_code",od.getDeviceCode()).eq("del_flag",0)));

			lsNewData.add(gv);
		}

		pageList1.setRecords(lsNewData);
		result.setSuccess(true);
		result.setResult(pageList1);
		return result;
	}
	@ApiOperation(value = "获取设备属性分页列表", tags = "设备管理")
	@RequestMapping(value = "/listProperty", method = RequestMethod.GET)
	public Result<IPage<DeviceProperty>> queryPropertyPageList(DeviceProperty property,
																@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																HttpServletRequest req) {
		Result<IPage<DeviceProperty>> result = new Result<IPage<DeviceProperty>>();
		QueryWrapper<DeviceProperty> queryWrapper = QueryGenerator.initQueryWrapper(property, req.getParameterMap());
		Page<DeviceProperty> page = new Page<DeviceProperty>(pageNo, pageSize);
		IPage<DeviceProperty> pageList = devicePropertyService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ApiOperation(value = "获取设备功能分页列表", tags = "设备管理")
	@RequestMapping(value = "/listFunction", method = RequestMethod.GET)
	public Result<IPage<DeviceFunction>> queryFunctionList(DeviceFunction function,
															@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															HttpServletRequest req) {
		Result<IPage<DeviceFunction>> result = new Result<IPage<DeviceFunction>>();
		QueryWrapper<DeviceFunction> queryWrapper = QueryGenerator.initQueryWrapper(function, req.getParameterMap());
		Page<DeviceFunction> page = new Page<DeviceFunction>(pageNo, pageSize);
		IPage<DeviceFunction> pageList = deviceFunctionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 *   此方法被弃用
	 * @param device
	 * @return
	 */
	@ApiOperation(value = "添加设备信息", tags = "设备管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Device> add(@RequestBody CloudDevice device) {
		Result<Device> result = new Result<Device>();
		try {
			Device o=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",device.getDeviceCode()));
			if(o==null) {//对象不存在则添加
				device.setCreateTime(new Date());
				Device nd=new Device();
				BeanUtils.copyProperties(device,nd);
				nd.setProtocolType("modbus-rtu"); //指定为modbus-rtu
				nd.setGatewayType("hardware"); //指定为硬件网关，即边缘计算网关
				nd.setCreateTime(new Date());
				nd.setEnableStatus(1);//启用状态
				deviceService.save(nd);
				if(device.getLsProperty().size()>0)deviceFunctionService.saveBatch(device.getLsProperty());
				if(device.getLsFunction().size()>0)deviceFunctionService.saveBatch(device.getLsFunction());

				result.setResult(nd);
				result.success("添加成功！");
			}else{
				result.setResult(o);
				result.error500("操作失败!对象已存在!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "批量添加设备信息", tags = "设备管理")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Device> batchAdd(@RequestBody List<CloudDevice> lsData) {
		Result<Device> result = new Result<Device>();
		try {
			for(int i=0;i<lsData.size();i++){
				CloudDevice device=(CloudDevice)lsData.get(i);
				Device o=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",device.getDeviceCode()));
				Device gateway=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",device.getGwDevCode())); //网关
				if(o==null) {//对象不存在则添加
					device.setCreateTime(new Date());
					Device nd=new Device();
					BeanUtils.copyProperties(device,nd);
					nd.setCreateBy(gateway.getCreateBy());
					nd.setCreateTime(new Date());
					nd.setEnableStatus(1);//启用状态
					nd.setProtocolType("modbus-rtu"); //指定为modbus-rtu
					nd.setGatewayType("hardware"); //指定为硬件网关，即边缘计算网关
					deviceService.save(nd);
					if(device.getLsProperty()!=null&&device.getLsProperty().size()>0)devicePropertyService.saveBatch(device.getLsProperty());
					if(device.getLsFunction()!=null&&device.getLsFunction().size()>0)deviceFunctionService.saveBatch(device.getLsFunction());

				}else{ //更新设备列表
					//Device nd=new Device();
					//BeanUtils.copyProperties(device,o);
					o.setCreateBy(gateway.getCreateBy());
					o.setModifyBy(gateway.getCreateBy());
					o.setModifyTime(new Date());
					o.setUpdateTime(new Date());
					o.setDeviceName(device.getDeviceName());
					o.setNetworkType(device.getNetworkType());
					o.setEnableStatus(1);//启用状态
					o.setProtocolType("modbus-rtu"); //指定为modbus-rtu
					o.setGatewayType("hardware"); //指定为硬件网关，即边缘计算网关
					deviceService.updateById(o);
					if(device.getLsProperty()!=null&&device.getLsProperty().size()>0){ //批量保存或更新属性
						List lsProperty=device.getLsProperty();
						for(int p=0;p<lsProperty.size();p++){
							String rstr=JSON.toJSONString(lsProperty.get(p));
							DeviceProperty dp=JSON.parseObject(rstr,DeviceProperty.class);
							DeviceProperty deviceProperty=devicePropertyService.getOne(new QueryWrapper<DeviceProperty>().eq("identifier",dp.getIdentifier()));
							if(deviceProperty==null){ //属性不存在，则新增属性
								dp.setStatus(1);//启用属性状态
								devicePropertyService.save(dp);
							}else{
								deviceProperty.setStatus(1);//启用属性状态
								deviceProperty.setName(dp.getName());
								deviceProperty.setDataType(dp.getDataType());
								//BeanUtils.copyProperties(dp,deviceProperty);
								devicePropertyService.updateById(deviceProperty);
							}
						}
					}
					if(device.getLsFunction()!=null&&device.getLsFunction().size()>0){//批量保存或更新功能
						List lsFunction=device.getLsFunction();
						for(int p=0;p<lsFunction.size();p++){
							DeviceFunction df=(DeviceFunction)lsFunction.get(p);
							DeviceFunction deviceFunction=deviceFunctionService.getOne(new QueryWrapper<DeviceFunction>().eq("identifier",df.getIdentifier()));
							if(deviceFunction==null){ //属性不存在，则新增属性
								df.setStatus(1);//启用属性状态
								deviceFunctionService.save(df);
							}else{
								deviceFunction.setName(df.getName());
								deviceFunction.setDataType(df.getDataType());
								deviceFunction.setStatus(1);//启用属性状态
								//BeanUtils.copyProperties(df,deviceFunction);
								deviceFunctionService.updateById(deviceFunction);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加设备属性信息", tags = "设备管理")
	@RequestMapping(value = "/addProperty", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DeviceProperty> addProperty(@RequestBody DeviceProperty property) {
		Result<DeviceProperty> result = new Result<DeviceProperty>();
		try {
			property.setCreateTime(new Date());
			devicePropertyService.save(property);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加设备功能信息", tags = "设备管理")
	@RequestMapping(value = "/addFunction", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DeviceFunction> addFunction(@RequestBody DeviceFunction function) {
		Result<DeviceFunction> result = new Result<DeviceFunction>();
		try {
			function.setCreateTime(new Date());
			deviceFunctionService.save(function);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  编辑
	 * @param device
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "修改设备信息", tags = "设备管理")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Device> edit(@RequestBody Device device) {
		Result<Device> result = new Result<Device>();
		Device sysrole = deviceService.getById(device.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = deviceService.updateById(device);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改设备属性信息", tags = "设备管理")
	@RequestMapping(value = "/editProperty", method = RequestMethod.PUT)
	public Result<DeviceProperty> editProperty(@RequestBody DeviceProperty property) {
		Result<DeviceProperty> result = new Result<DeviceProperty>();
		DeviceProperty sysrole = devicePropertyService.getById(property.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = devicePropertyService.updateById(property);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改设备功能信息", tags = "设备管理")
	@RequestMapping(value = "/editFunction", method = RequestMethod.PUT)
	public Result<DeviceFunction> editFunction(@RequestBody DeviceFunction function) {
		Result<DeviceFunction> result = new Result<DeviceFunction>();
		DeviceFunction sysrole = deviceFunctionService.getById(function.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = deviceFunctionService.updateById(function);
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
	@ApiOperation(value = "删除设备信息", tags = "设备管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		//deviceService.removeById(id);
		Device p = deviceService.getById(id);
		p.setDelFlag(1); //软删除
		deviceService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除属性信息", tags = "设备管理")
	@RequestMapping(value = "/deleteProperty", method = RequestMethod.DELETE)
	public Result<?> deleteProperty(@RequestParam(name="propertyId",required=true) String propertyId) {
		//deviceService.removeById(id);
		DeviceProperty p = devicePropertyService.getById(propertyId);
		p.setDelFlag(1); //软删除
		devicePropertyService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除功能信息", tags = "设备管理")
	@RequestMapping(value = "/deleteFunction", method = RequestMethod.DELETE)
	public Result<?> deleteFunction(@RequestParam(name="functionId",required=true) String functionId) {
		//deviceService.removeById(id);
		DeviceFunction p = deviceFunctionService.getById(functionId);
		p.setDelFlag(1); //软删除
		deviceFunctionService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除设备信息", tags = "设备管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Device> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Device> result = new Result<Device>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			deviceService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除角色成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询设备信息", tags = "设备管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Device> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Device> result = new Result<Device>();
		Device sysrole = deviceService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有设备信息", tags = "设备管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Device>> queryall() {
		Result<List<Device>> result = new Result<>();
		List<Device> list = deviceService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有在线设备", tags = "设备管理")
	@RequestMapping(value = "/queryOnline", method = RequestMethod.GET)
	public Result<List<Device>> queryOnline() {
		Result<List<Device>> result = new Result<>();

		List<Device> list = deviceService.list(new QueryWrapper<Device>().eq("active_status","1"));
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有离线设备", tags = "设备管理")
	@RequestMapping(value = "/queryOffline", method = RequestMethod.GET)
	public Result<List<Device>> queryOffline() {
		Result<List<Device>> result = new Result<>();
		List<Device> list = deviceService.list(new QueryWrapper<Device>().eq("active_status","0"));
		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有设备属性信息", tags = "设备管理")
	@RequestMapping(value = "/queryAllProperty", method = RequestMethod.GET)
	public Result<List<DeviceProperty>> queryallProperty(@RequestParam(name="deviceCode",required=true) String deviceCode) {
		Result<List<DeviceProperty>> result = new Result<>();
		QueryWrapper<DeviceProperty> queryWrapper = new QueryWrapper<DeviceProperty>();
		queryWrapper.eq("deviceCode",deviceCode);
		List<DeviceProperty> list = devicePropertyService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备属性信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有设备功能信息", tags = "设备管理")
	@RequestMapping(value = "/queryAllFunction", method = RequestMethod.GET)
	public Result<List<DeviceFunction>> queryallFunction(@RequestParam(name="deviceCode",required=true) String deviceCode) {
		Result<List<DeviceFunction>> result = new Result<>();
		QueryWrapper<DeviceFunction> queryWrapper = new QueryWrapper<DeviceFunction>();
		queryWrapper.eq("deviceCode",deviceCode);
		List<DeviceFunction> list = deviceFunctionService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备功能信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 校验设备编码唯一
	 */
	@ApiOperation(value = "检测设备编码唯一", tags = "设备管理")
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证设备编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			Device role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = deviceService.getById(id);
			}
			Device newRole = deviceService.getOne(new QueryWrapper<Device>().lambda().eq(Device::getDeviceCode, code));
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
	@ApiOperation(value = "检测设备属性编码唯一", tags = "设备管理")
	@RequestMapping(value = "/checkPropertyCode", method = RequestMethod.GET)
	public Result<Boolean> checkPropertyCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证设备属性编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			DeviceProperty role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = devicePropertyService.getById(id);
			}
			DeviceProperty newRole = devicePropertyService.getOne(new QueryWrapper<DeviceProperty>().lambda().eq(DeviceProperty::getIdentifier, code));
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

	@ApiOperation(value = "检测设备功能编码唯一", tags = "设备管理")
	@RequestMapping(value = "/checkFunctionCode", method = RequestMethod.GET)
	public Result<Boolean> checkFunctionCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证设备功能编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			DeviceFunction role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = deviceFunctionService.getById(id);
			}
			DeviceFunction newRole = deviceFunctionService.getOne(new QueryWrapper<DeviceFunction>().lambda().eq(DeviceFunction::getIdentifier, code));
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
	//@ApiOperation(value = "导出excel设备信息", tags = "设备管理")
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Device sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Device> pageList = deviceService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,Device.class);
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
	@ApiOperation(value = "导出excel设备信息", tags = "设备管理")
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
				//return deviceService.importExcelCheckRoleCode(file, params);
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

	/**
	 * 下发属性
	 * @param reqDto
	 * @return
	 */
	@RequestMapping(value = "propertySetValue", method = RequestMethod.POST)
	@ApiOperation(value = "下发属性设置值", httpMethod = "POST", response = DataResult.class,  notes = "设备管理")
	public DataResult propertySet(@Valid @RequestBody PropertySetReqDto reqDto) {
		log.info("属性设置请求...");
		MQSendMessageBo bo = new MQSendMessageBo();
		bo.setIdentifier(reqDto.getIdentifier());
		bo.setDeviceCode(reqDto.getDeviceCode());
		Device device = deviceService.getOne(new QueryWrapper<Device>().eq("device_code",reqDto.getDeviceCode()));
		bo.setProductCode(device.getProductCode());
		JSONObject jo=new JSONObject();
		jo.put(reqDto.getIdentifier(),reqDto.getValue());
		bo.setCommand(jo);//命令

		//redis写入
		bo.setMessageId(IdGenerate.genId());
		redisDao.publish(SXConstants.REDIS_CHANNEL.PROP_SET, JSON.toJSONString(bo));

		//重发三次共4秒，防止断线收不到，20220703新策略
//		try {
//			for (int i = 0; i < 2; i++) {
//				Thread.sleep(2000); //2秒重发一次
//				redisDao.publish(SXConstants.REDIS_CHANNEL.PROP_SET, JSON.toJSONString(bo));
//			}
//		}catch(Exception e){
//
//		}		try {
//			for (int i = 0; i < 2; i++) {
//				Thread.sleep(2000); //2秒重发一次
//				redisDao.publish(SXConstants.REDIS_CHANNEL.PROP_SET, JSON.toJSONString(bo));
//			}
//		}catch(Exception e){
//
//		}
		return DataResult.ok();
	}

}
