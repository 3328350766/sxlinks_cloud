package com.sxlinks.modules.system.controller.productCenter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.domain.DataResult;
import com.sxlinks.common.domain.dto.PageReqDto;
import com.sxlinks.common.domain.dto.PageResDto;
import com.sxlinks.common.domain.dto.request.device.DeviceRtItemReqDto;
import com.sxlinks.common.domain.dto.request.device.InvokeReqDto;
import com.sxlinks.common.domain.dto.request.device.PropertySetReqDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtResDto;
import com.sxlinks.common.domain.gateway.mq.MQSendMessageBo;
import com.sxlinks.common.enums.BCErrorEnum;
import com.sxlinks.common.metadata.ProductModelTypeEnum;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.productCenter.*;
import com.sxlinks.modules.system.service.dataCenter.IDeviceDataService;
import com.sxlinks.modules.system.service.productCenter.*;
import com.sxlinks.storage.IDataCenterService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
@RequestMapping("/productCenter/device")
@Slf4j
@Api(value = "产品中心", tags = {"设备管理"})
public class DeviceController {
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyService devicePropertyService;
	@Autowired
	private IDeviceFunctionService deviceFunctionService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductPropertyService productPropertyService;
	@Autowired
	private IProductFunctionService productFunctionService;
	@Autowired
	private IDeviceDataService deviceDataService;
	@Autowired
	private IServiceInvoke serviceInvoke;	//设备下发服务调用
	@Autowired
    RedisDaoImpl redisDao; //redis缓存
	@Resource
    IDataCenterService dataCenterService;   //es索引操作类
	@Autowired
	private IModbusPointService modbusPointService;
	@Autowired
	private IStringPointService stringPointService;
	@Autowired
	private IJsonPointService jsonPointService;

	@Resource
	private BaseCommonService baseCommonService;
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
	public JSONObject queryPageList(Device device,
                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                    HttpServletRequest req) {
		Result<IPage<Device>> result = new Result<IPage<Device>>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		queryWrapper.eq("del_flag",0);
		//queryWrapper.ne("node_type","GATEWAY"); //网关产品不显示

		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));

		int deviceCount=0;	//设备总数
		QueryWrapper queryDevice=new QueryWrapper<Device>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDevice.eq("create_by",user.getId());
		deviceCount=deviceService.count(queryDevice);
		jo.put("deviceCount",deviceCount);

		int deviceEnableCount=0;	//设备激活总数
		QueryWrapper queryDeviceEnable=new QueryWrapper<Device>().eq("enable_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceEnable.eq("create_by",user.getId());
		deviceEnableCount=deviceService.count(queryDeviceEnable);
		jo.put("deviceEnableCount",deviceEnableCount);

		int deviceOnlineCount=0;	//设备在线总数
		QueryWrapper queryDeviceOnline=new QueryWrapper<Device>().eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOnline.eq("create_by",user.getId());
		deviceOnlineCount=deviceService.count(queryDeviceOnline);
		jo.put("deviceOnlineCount",deviceOnlineCount);

		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取设备数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return jo;
	}

	@RequestMapping(value = "/runtime", method = RequestMethod.GET)
	@ApiOperation(value = "设备运行状态", httpMethod = "GET", response = DataResult.class,tags = "设备管理")
	public DataResult<List<DeviceRtResDto>> runtime(String deviceCode, String type) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if (StringUtils.isEmpty(deviceCode)) {
			return DataResult.fail(BCErrorEnum.INVALID_PARAM);
		}

		List<DeviceRtResDto> result = deviceService.queryRtByDevCode(deviceCode);
		return DataResult.ok(result);
	}

	@RequestMapping(value = "/queryPropertySetHistoryData", method = RequestMethod.POST)
	@ApiOperation(value = "查看设备具体属性历史设置列表", httpMethod = "POST", response = DataResult.class, tags = "设备管理")
	public DataResult<PageResDto<DeviceRtHistoryResDto>>
	setItem(@RequestBody PageReqDto<DeviceRtItemReqDto> searchPage) {
		PageResDto<DeviceRtHistoryResDto> pageResult = deviceDataService.searchSetItem(searchPage);
		return DataResult.ok(pageResult);
	}
	@RequestMapping(value = "/queryPropertyDataHistoryData", method = RequestMethod.POST)
	@ApiOperation(value = "查看设备具体属性历史数据列表", httpMethod = "POST", response = DataResult.class, notes = "设备管理")
	public DataResult<PageResDto<DeviceRtHistoryResDto>>
	rtItem(@RequestBody PageReqDto<DeviceRtItemReqDto> searchPage) {
		PageResDto<DeviceRtHistoryResDto> pageResult = deviceDataService.searchReportItem(searchPage);
		return DataResult.ok(pageResult);
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
		return DataResult.ok();
	}
	@RequestMapping(value = "serviceInvoke", method = RequestMethod.POST)
	@ApiOperation(value = "下发指令", httpMethod = "POST", response = DataResult.class,  notes = "设备管理")
	public DataResult invoke(@Valid @RequestBody InvokeReqDto reqDto) {
		log.info("下发指令请求...");
		String messageId = serviceInvoke.invokeNoReply(reqDto);
		return DataResult.ok(messageId);
	}
	@RequestMapping(value = "serviceInvokeReply", method = RequestMethod.POST)
	@ApiOperation(value = "下发指令带回执", httpMethod = "POST", response = DataResult.class,  notes = "设备管理")
	public DataResult invokeReply(@Valid @RequestBody InvokeReqDto reqDto) {
		log.info("下发指令请求...");
		Map reply = serviceInvoke.invokeWithReply(reqDto);
		return DataResult.ok(reply);
	}

	@ApiOperation(value = "获取设备属性分页列表", tags = "设备管理")
	@RequestMapping(value = "/listProperty", method = RequestMethod.GET)
	public Result<IPage<DeviceProperty>> queryPropertyPageList(DeviceProperty property,
                                                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                               HttpServletRequest req) {
		Result<IPage<DeviceProperty>> result = new Result<IPage<DeviceProperty>>();
		QueryWrapper<DeviceProperty> queryWrapper = QueryGenerator.initQueryWrapper(property, req.getParameterMap());
		if(StringUtils.isEmpty(property.getDeviceCode())){
			queryWrapper.eq("device_code","");
		}

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
	 * @param device
	 * @return
	 */
	@ApiOperation(value = "添加设备信息", tags = "设备管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Device> add(@RequestBody Device device) {
		Result<Device> result = new Result<Device>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			device.setEnableStatus(1); //激活状态 0-未激活 1-已激活
			device.setCreateBy(user.getId());
			device.setCreateTime(new Date());
			device.setModifyTime(new Date());
			device.setModifyBy(user.getId());
			deviceService.save(device);
			//继承产品的属性和功能
			List lsProductProperty=productPropertyService.list(new QueryWrapper<ProductProperty>().eq("product_code",device.getProductCode()));
			for(int i=0;i<lsProductProperty.size();i++){
				ProductProperty pp=(ProductProperty)lsProductProperty.get(i);
				DeviceProperty dp=new DeviceProperty();
				dp.setName(pp.getName());
				dp.setUnit(pp.getUnit());
				dp.setUnitName(pp.getUnitName());
				dp.setDataType(pp.getDataType());
				dp.setIdentifier(device.getDeviceCode()+"_"+pp.getIdentifier());
				dp.setStatus(1); //默认启用
				dp.setDelFlag(0); //未删除
				dp.setDeviceCode(device.getDeviceCode());
				dp.setProductCode(device.getProductCode());
				dp.setWrType(0);
				dp.setAttr(pp.getAttr());
				dp.setFuncDesc(pp.getFuncDesc());
				dp.setAsync(1);
				dp.setEventType("INFO");

				JSONObject jo=new JSONObject();
				jo.put("dataType",pp.getDataType());
				jo.put("identifier",dp.getIdentifier());
				jo.put("unit",pp.getUnit());
				jo.put("length",null); //长度，目前类没有
				dp.setAttr(jo.toJSONString()); //手动设置属性压入attr

				dp.setCreateBy(user.getId());
				dp.setCreateTime(new Date());
				dp.setFuncType("PROP");

				devicePropertyService.save(dp);
			}

			List lsProductFunction=productFunctionService.list(new QueryWrapper<ProductFunction>().eq("product_code",device.getProductCode()));
			for(int i=0;i<lsProductFunction.size();i++){
				ProductFunction pf=(ProductFunction)lsProductFunction.get(i);
				DeviceFunction df=new DeviceFunction();

				df.setName(pf.getName());
				df.setUnit(pf.getUnit());
				df.setUnitName(pf.getUnitName());
				df.setIdentifier(device.getDeviceCode()+"_"+pf.getIdentifier());
				df.setDataType(pf.getDataType());
				df.setStatus(1); //默认启用
				df.setDelFlag(0); //未删除
				df.setDeviceCode(device.getDeviceCode());
				df.setProductCode(device.getProductCode());
				df.setWrType(0);
				df.setAttr(pf.getAttr());
				df.setFuncDesc(pf.getFuncDesc());
				df.setAsync(1);
				df.setEventType("INFO");

				JSONObject jo=new JSONObject();
				jo.put("dataType",pf.getDataType());
				jo.put("identifier",df.getIdentifier());
				jo.put("unit",pf.getUnit());
				jo.put("length",null); //长度，目前类没有
				df.setAttr(jo.toJSONString()); //手动设置属性压入attr

				df.setCreateBy(user.getId());
				df.setCreateTime(new Date());
				df.setFuncType("FUNC");
				deviceFunctionService.save(df);
			}
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 *   复制
	 * @param device
	 * @return
	 */
	@ApiOperation(value = "复制设备信息", tags = "设备管理")
	@RequestMapping(value = "/copy", method = RequestMethod.PUT)
	//@RequiresRoles({"admin"})
	public Result<Device> copy(@RequestBody Device device) {
		Result<Device> result = new Result<Device>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		try {
			Device od=deviceService.getById(device.getId());
			Device nd=new Device();
			BeanUtils.copyProperties(od,nd);
			nd.setId(null); //置为空
			nd.setDeviceName(device.getDeviceName());
			nd.setDeviceCode(device.getDeviceCode());
			nd.setProjectId(device.getProjectId());
			nd.setDeviceSecret(device.getDeviceSecret());
			nd.setEnableStatus(device.getEnableStatus());
			nd.setLocationType(device.getLocationType());
			nd.setLng(device.getLng());
			nd.setLat(device.getLat());
			deviceService.save(nd);
			//继承产品的属性和功能
			List lsDeviceProperty=devicePropertyService.list(new QueryWrapper<DeviceProperty>().eq("device_code",od.getDeviceCode()));
			for(int i=0;i<lsDeviceProperty.size();i++){
				DeviceProperty odp=(DeviceProperty)lsDeviceProperty.get(i);
				DeviceProperty ndp=new DeviceProperty();
				BeanUtils.copyProperties(odp,ndp);
				ndp.setId(null);
				ndp.setDeviceCode(nd.getDeviceCode()); //赋新的设备编码
				ndp.setIdentifier(odp.getIdentifier().replace(od.getDeviceCode(),nd.getDeviceCode())); //将新属性编码中的旧设备编码替换成新设备编码
				ndp.setAttr(odp.getAttr().replace(od.getDeviceCode(),nd.getDeviceCode()));
				devicePropertyService.save(ndp);
				//复制点位数据
				if(!StringUtils.isEmpty(odp.getPointType())&&odp.getPointType().equals("modbus")){
					List lsModbusPoint=modbusPointService.list(new QueryWrapper<ModbusPoint>().eq("device_code",od.getDeviceCode()).eq("point_code",odp.getIdentifier()));
					for(int j=0;j<lsModbusPoint.size();j++){
						ModbusPoint op=(ModbusPoint)lsModbusPoint.get(j);
						ModbusPoint np=new ModbusPoint();
						BeanUtils.copyProperties(op,np);
						np.setId(null);
						np.setPointId(ndp.getId());
						np.setPointCode(ndp.getIdentifier());
						np.setDeviceId(nd.getId());
						np.setDeviceCode(nd.getDeviceCode());
						modbusPointService.save(np);
					}
				}
				if(!StringUtils.isEmpty(odp.getPointType())&&odp.getPointType().equals("json")){
					List lsJsonPoint=jsonPointService.list(new QueryWrapper<JsonPoint>().eq("device_code",od.getDeviceCode()).eq("point_code",odp.getIdentifier()));
					for(int j=0;j<lsJsonPoint.size();j++){
						JsonPoint op=(JsonPoint)lsJsonPoint.get(j);
						JsonPoint np=new JsonPoint();
						BeanUtils.copyProperties(op,np);
						np.setId(null);
						np.setPointId(ndp.getId());
						np.setPointCode(ndp.getIdentifier());
						np.setDeviceId(nd.getId());
						np.setDeviceCode(nd.getDeviceCode());
						jsonPointService.save(np);
					}
				}
				if(!StringUtils.isEmpty(odp.getPointType())&&odp.getPointType().equals("string")){
					List lsStringPoint=stringPointService.list(new QueryWrapper<StringPoint>().eq("device_code",od.getDeviceCode()).eq("point_code",odp.getIdentifier()));
					for(int j=0;j<lsStringPoint.size();j++){
						StringPoint op=(StringPoint)lsStringPoint.get(j);
						StringPoint np=new StringPoint();
						BeanUtils.copyProperties(op,np);
						np.setId(null);
						np.setPointId(ndp.getId());
						np.setPointCode(ndp.getIdentifier());
						np.setDeviceId(nd.getId());
						np.setDeviceCode(nd.getDeviceCode());
						stringPointService.save(np);
					}
				}
			}

			List lsDeviceFunction=deviceFunctionService.list(new QueryWrapper<DeviceFunction>().eq("device_code",od.getDeviceCode()));
			for(int i=0;i<lsDeviceFunction.size();i++){
				DeviceFunction odf=(DeviceFunction)lsDeviceFunction.get(i);
				DeviceFunction ndf=new DeviceFunction();
				BeanUtils.copyProperties(odf,ndf);
				ndf.setId(null);
				ndf.setDeviceCode(nd.getDeviceCode()); //赋新的设备编码
				ndf.setIdentifier(odf.getIdentifier().replace(od.getDeviceCode(),nd.getDeviceCode())); //将新属性编码中的旧设备编码替换成新设备编码
				ndf.setAttr(odf.getAttr().replace(od.getDeviceCode(),nd.getDeviceCode()));
				deviceFunctionService.save(ndf);
			}
			result.success("复制成功！");
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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			property.setCreateBy(user.getId());
			property.setCreateTime(new Date());
			property.setFuncType("PROP");
			JSONObject jo=new JSONObject();
			jo.put("dataType",property.getDataType());
			jo.put("identifier",property.getIdentifier());
			jo.put("unit",property.getUnit());
			jo.put("length",null); //长度，目前类没有
			property.setAttr(jo.toJSONString()); //手动设置属性压入attr
			devicePropertyService.save(property);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "保存设备属性的modbus点位信息", tags = "设备管理")
	@RequestMapping(value = "/saveModbusPoint", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<ModbusPoint> saveModbusPoint(@RequestParam(name="propertyCode", defaultValue="") String propertyCode,
                                               @RequestParam(name="address", defaultValue="") String address,
                                               @RequestParam(name="code", defaultValue="") String code,
                                               @RequestParam(name="startRegister", defaultValue="") String startRegister,
                                               @RequestParam(name="dataType", defaultValue="") String dataType,
                                               @RequestParam(name="byteSort", defaultValue="") String byteSort,
                                               @RequestParam(name="state", defaultValue="") String state, HttpServletRequest req) {
		//propertyCode-设备属性编码 address-设备从站地址 code-功能码 startRegister-寄存器地址 dataType-数据类型 byteSort-字节顺序 state-状态
		Result<ModbusPoint> result = new Result<ModbusPoint>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			ModbusPoint point=new ModbusPoint();
			ModbusPoint point1=modbusPointService.getOne(new QueryWrapper<ModbusPoint>().eq("point_code",propertyCode));
			DeviceProperty deviceProperty=devicePropertyService.getOne(new QueryWrapper<DeviceProperty>().eq("identifier",propertyCode));
			Device device=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",deviceProperty.getDeviceCode()));
			if(point1==null){//不存在则新增
				point.setDeviceId(device.getId());
				point.setDeviceCode(device.getDeviceCode());
				point.setPointId(deviceProperty.getId());
				point.setPointCode(propertyCode);
				//网关不需要绑定

				point.setAddress(address);
				point.setCode(code);
				point.setStartRegister(startRegister);
				point.setDataType(dataType);
				point.setDataLength("2"); //数据长度为2
				point.setByteSort(byteSort);

				point.setCreateBy(user.getId());
				point.setCreateTime(new Date());
				point.setState(state);
				modbusPointService.save(point);
			}else{//存在则修改
				point1.setAddress(address);
				point1.setCode(code);
				point1.setStartRegister(startRegister);
				point1.setDataType(dataType);
				point1.setDataLength("2"); //数据长度为2
				point1.setByteSort(byteSort);

				point1.setModifyBy(user.getId());
				point1.setModifyTime(new Date());
				point1.setState(state);
				modbusPointService.updateById(point1);
			}

			result.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "获取设备属性的modbus点位信息", tags = "设备管理")
	@RequestMapping(value = "/queryModbusPointByDevicePropertyCode", method = RequestMethod.GET)
	public Result<ModbusPoint> queryModbusPointByDevicePropertyCode(@RequestParam(name="propertyCode",required=true) String propertyCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<ModbusPoint> result = new Result<ModbusPoint>();
		ModbusPoint point=modbusPointService.getOne(new QueryWrapper<ModbusPoint>().eq("point_code",propertyCode));
		if(point==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(point);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "保存设备属性的string点位信息", tags = "设备管理")
	@RequestMapping(value = "/saveStringPoint", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<StringPoint> saveStringPoint(@RequestParam(name="propertyCode", defaultValue="") String propertyCode,
                                               @RequestParam(name="matchMethod", defaultValue="") String matchMethod,
                                               @RequestParam(name="splitString", defaultValue="") String splitString,
                                               @RequestParam(name="varName", defaultValue="") String varName,
                                               @RequestParam(name="replaceString", defaultValue="") String replaceString,
                                               @RequestParam(name="startPos", defaultValue="") Integer startPos,
                                               @RequestParam(name="finishPos", defaultValue="") Integer finishPos,

                                               @RequestParam(name="state", defaultValue="") String state, HttpServletRequest req) {
		//propertyCode-设备属性编码 matchMethod-匹配方式 splitString-分隔字符串 varName-变量名 state-状态
		Result<StringPoint> result = new Result<StringPoint>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			StringPoint point=new StringPoint();
			StringPoint point1=stringPointService.getOne(new QueryWrapper<StringPoint>().eq("point_code",propertyCode));
			DeviceProperty deviceProperty=devicePropertyService.getOne(new QueryWrapper<DeviceProperty>().eq("identifier",propertyCode));
			Device device=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",deviceProperty.getDeviceCode()));
			if(point1==null){//不存在则新增
				point.setDeviceId(device.getId());
				point.setDeviceCode(device.getDeviceCode());
				point.setPointId(deviceProperty.getId());
				point.setPointCode(propertyCode);
				//网关不需要绑定

				point.setMatchMethod(matchMethod);
				point.setSplitString(splitString);
				point.setVarName(varName);
				point.setReplaceString(replaceString);
				point.setStartPos(startPos);
				point.setFinishPos(finishPos);

				point.setCreateBy(user.getId());
				point.setCreateTime(new Date());
				point.setState(state);
				stringPointService.save(point);
			}else{//存在则修改
				point1.setMatchMethod(matchMethod);
				point1.setSplitString(splitString);
				point1.setVarName(varName);
				point1.setReplaceString(replaceString);
				point1.setStartPos(startPos);
				point1.setFinishPos(finishPos);

				point1.setModifyBy(user.getId());
				point1.setModifyTime(new Date());
				point1.setState(state);
				stringPointService.updateById(point1);
			}

			result.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "获取设备属性的string点位信息", tags = "设备管理")
	@RequestMapping(value = "/queryStringPointByDevicePropertyCode", method = RequestMethod.GET)
	public Result<StringPoint> queryStringPointByDevicePropertyCode(@RequestParam(name="propertyCode",required=true) String propertyCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<StringPoint> result = new Result<StringPoint>();
		StringPoint point=stringPointService.getOne(new QueryWrapper<StringPoint>().eq("point_code",propertyCode));
		if(point==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(point);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "保存设备属性的json点位信息", tags = "设备管理")
	@RequestMapping(value = "/saveJsonPoint", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<JsonPoint> saveJsonPoint(@RequestParam(name="propertyCode", defaultValue="") String propertyCode,
                                           @RequestParam(name="matchObject", defaultValue="") String matchObject,
                                           @RequestParam(name="matchMethod", defaultValue="") String matchMethod,
                                           @RequestParam(name="varName", defaultValue="") String varName,

                                           @RequestParam(name="state", defaultValue="") String state, HttpServletRequest req) {
		//propertyCode-设备属性编码 matchObject-匹配对象 matchMethod-匹配方式 varName-变量名称 state-状态
		Result<JsonPoint> result = new Result<JsonPoint>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			JsonPoint point=new JsonPoint();
			JsonPoint point1=jsonPointService.getOne(new QueryWrapper<JsonPoint>().eq("point_code",propertyCode));
			DeviceProperty deviceProperty=devicePropertyService.getOne(new QueryWrapper<DeviceProperty>().eq("identifier",propertyCode));
			Device device=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",deviceProperty.getDeviceCode()));
			if(point1==null){//不存在则新增
				point.setDeviceId(device.getId());
				point.setDeviceCode(device.getDeviceCode());
				point.setPointId(deviceProperty.getId());
				point.setPointCode(propertyCode);
				//网关不需要绑定

				point.setMatchObject(matchObject);
				point.setMatchMethod(matchMethod);
				point.setVarName(varName);

				point.setCreateBy(user.getId());
				point.setCreateTime(new Date());
				point.setState(state);
				jsonPointService.save(point);
			}else{//存在则修改
				point1.setMatchObject(matchObject);
				point1.setMatchMethod(matchMethod);
				point1.setVarName(varName);

				point1.setModifyBy(user.getId());
				point1.setModifyTime(new Date());
				point1.setState(state);
				jsonPointService.updateById(point1);
			}

			result.success("保存成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	@ApiOperation(value = "获取设备属性的json点位信息", tags = "设备管理")
	@RequestMapping(value = "/queryJsonPointByDevicePropertyCode", method = RequestMethod.GET)
	public Result<JsonPoint> queryJsonPointByDevicePropertyCode(@RequestParam(name="propertyCode",required=true) String propertyCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<JsonPoint> result = new Result<JsonPoint>();
		JsonPoint point=jsonPointService.getOne(new QueryWrapper<JsonPoint>().eq("point_code",propertyCode));
		if(point==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(point);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "添加设备功能信息", tags = "设备管理")
	@RequestMapping(value = "/addFunction", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DeviceFunction> addFunction(@RequestBody DeviceFunction function) {
		Result<DeviceFunction> result = new Result<DeviceFunction>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try {
			function.setCreateBy(user.getId());
			function.setCreateTime(new Date());
			function.setFuncType("FUNC");
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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		device.setModifyBy(user.getId());
		device.setModifyTime(new Date());
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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		JSONObject jo=new JSONObject();
		jo.put("dataType",property.getDataType());
		jo.put("identifier",property.getIdentifier());
		jo.put("unit",property.getUnit());
		jo.put("length",null); //长度，目前类没有
		property.setAttr(jo.toJSONString()); //手动设置属性压入attr
		property.setModifyBy(user.getId());
		property.setModifyTime(new Date());
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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		function.setModifyBy(user.getId());
		function.setModifyTime(new Date());
		JSONObject jo=new JSONObject();
		jo.put("dataType",function.getDataType());
		jo.put("identifier",function.getIdentifier());
		jo.put("unit",function.getUnit());
		jo.put("length",null); //长度，目前类没有
		function.setAttr(jo.toJSONString()); //手动设置属性压入attr
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
		Device d = deviceService.getById(id);
		//先删除该设备下的所有属性
		List lsProperty=devicePropertyService.list(new QueryWrapper<DeviceProperty>().eq("device_code",d.getDeviceCode()));
		for(int i=0;i<lsProperty.size();i++){
			DeviceProperty p=(DeviceProperty)lsProperty.get(i);
			/**清除es数据**/
			ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain("PROP");
			dataCenterService.deleteIndex(p.getProductCode(),typeEnum,p.getIdentifier());
			/**删除数据库中的数据**/
			devicePropertyService.removeById(p.getId());//硬删除
		}
		//先删除该设备下的所有功能
		List lsFunction=deviceFunctionService.list(new QueryWrapper<DeviceFunction>().eq("device_code",d.getDeviceCode()));
		for(int i=0;i<lsFunction.size();i++){
			DeviceFunction p=(DeviceFunction)lsFunction.get(i);
			/**清除es数据**/
			ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain("FUNC");
			dataCenterService.deleteIndex(p.getProductCode(),typeEnum,p.getIdentifier());
			/**删除数据库中的数据**/
			deviceFunctionService.removeById(p.getId());//硬删除
		}
		stringPointService.remove(new QueryWrapper<StringPoint>().eq("device_code",d.getDeviceCode()));
		jsonPointService.remove(new QueryWrapper<JsonPoint>().eq("device_code",d.getDeviceCode()));
		modbusPointService.remove(new QueryWrapper<ModbusPoint>().eq("device_code",d.getDeviceCode()));

		deviceService.removeById(d.getId()); //删除数据
//		p.setDelFlag(1); //软删除
//		deviceService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除属性信息", tags = "设备管理")
	@RequestMapping(value = "/deleteProperty", method = RequestMethod.DELETE)
	public Result<?> deleteProperty(@RequestParam(name="propertyId",required=true) String propertyId) {
		//deviceService.removeById(id);
		DeviceProperty p = devicePropertyService.getById(propertyId);
		/**清除es数据**/
		ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain("PROP");
		dataCenterService.deleteIndex(p.getProductCode(),typeEnum,p.getIdentifier());
		/**删除数据库中的数据**/
		devicePropertyService.removeById(propertyId);//硬删除
//		p.setDelFlag(1); //软删除
//		devicePropertyService.saveOrUpdate(p);

		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除功能信息", tags = "设备管理")
	@RequestMapping(value = "/deleteFunction", method = RequestMethod.DELETE)
	public Result<?> deleteFunction(@RequestParam(name="functionId",required=true) String functionId) {
		//deviceService.removeById(id);
		DeviceFunction p = deviceFunctionService.getById(functionId);
		/**清除es数据**/
		ProductModelTypeEnum typeEnum = ProductModelTypeEnum.explain("FUNC");
		dataCenterService.deleteIndex(p.getProductCode(),typeEnum,p.getIdentifier());
		/**删除数据库中的数据**/
		deviceFunctionService.removeById(functionId);//硬删除
//		p.setDelFlag(1); //软删除
//		deviceFunctionService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
//	/**
//	 *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	//@RequiresRoles({"admin"})
//	@ApiOperation(value = "批量删除设备信息", tags = "设备管理")
//	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
//	public Result<Device> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<Device> result = new Result<Device>();
//		if(oConvertUtils.isEmpty(ids)) {
//			result.error500("未选中角色！");
//		}else {
//			deviceService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除角色成功!");
//		}
//		return result;
//	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询设备信息", tags = "设备管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Device> queryById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
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

	@ApiOperation(value = "通过设备编码查询设备信息", tags = "设备管理")
	@RequestMapping(value = "/queryByDeviceCode", method = RequestMethod.GET)
	public Result<Device> queryByDeviceCode(@RequestParam(name="deviceCode",required=true) String deviceCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Device> result = new Result<Device>();
		Device device = deviceService.getOne(new QueryWrapper<Device>().eq("device_code",deviceCode));
		if(device==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(device);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有设备信息", tags = "设备管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Device>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Device>> result = new Result<>();
		QueryWrapper queryWrapper=new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());

		List<Device> list = deviceService.list(queryWrapper);
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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Device>> result = new Result<>();
		QueryWrapper queryWrapper=new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);
		queryWrapper.eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());

		List<Device> list = deviceService.list(queryWrapper);

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
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Device>> result = new Result<>();
		QueryWrapper queryWrapper=new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);
		queryWrapper.eq("active_status","0");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());

		List<Device> list = deviceService.list(queryWrapper);

		if(list==null||list.size()<=0) {
			result.error500("未找到设备信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询该设备的所有属性信息", tags = "设备管理")
	@RequestMapping(value = "/queryAllProperty", method = RequestMethod.GET)
	public Result<List<DeviceProperty>> queryallProperty(@RequestParam(name="deviceCode",required=false) String deviceCode,
                                                         @RequestParam(name="deviceId",required=false) String deviceId) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<DeviceProperty>> result = new Result<>();
		QueryWrapper<DeviceProperty> queryWrapper = new QueryWrapper<DeviceProperty>();
		if(!StringUtils.isEmpty(deviceId)){ //指定设备id不为空，则要先获取设备实体
			Device device=deviceService.getById(deviceId);
			deviceCode=device.getDeviceCode();
		}
		if(!StringUtils.isEmpty(deviceCode))queryWrapper.eq("device_code",deviceCode);

		List<DeviceProperty> list = devicePropertyService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备属性信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询该设备的所有功能信息", tags = "设备管理")
	@RequestMapping(value = "/queryAllFunction", method = RequestMethod.GET)
	public Result<List<DeviceFunction>> queryallFunction(@RequestParam(name="deviceCode",required=true) String deviceCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<DeviceFunction>> result = new Result<>();
		QueryWrapper<DeviceFunction> queryWrapper = new QueryWrapper<DeviceFunction>();
		queryWrapper.eq("device_code",deviceCode);
		List<DeviceFunction> list = deviceFunctionService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到设备功能信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "根据项目id获取设备分页列表", tags = "设备管理")
	@RequestMapping(value = "/listByProjectId", method = RequestMethod.GET)
	public JSONObject listByProjectId(@RequestParam(name="project", defaultValue="") Long projectId,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		Result<IPage<Device>> result = new Result<IPage<Device>>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Device> queryWrapper = new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);
		queryWrapper.ne("node_type","GATEWAY");
		queryWrapper.eq("project_id",projectId); //项目id
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));

		int deviceCount=0;	//设备总数
		QueryWrapper queryDevice=new QueryWrapper<Device>();
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDevice.eq("create_by",user.getId());
		deviceCount=deviceService.count(queryDevice);
		jo.put("deviceCount",deviceCount);

		int deviceEnableCount=0;	//设备激活总数
		QueryWrapper queryDeviceEnable=new QueryWrapper<Device>().eq("enable_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceEnable.eq("create_by",user.getId());
		deviceEnableCount=deviceService.count(queryDeviceEnable);
		jo.put("deviceEnableCount",deviceEnableCount);

		int deviceOnlineCount=0;	//设备在线总数
		QueryWrapper queryDeviceOnline=new QueryWrapper<Device>().eq("active_status","1");
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryDeviceOnline.eq("create_by",user.getId());
		deviceOnlineCount=deviceService.count(queryDeviceOnline);
		jo.put("deviceOnlineCount",deviceOnlineCount);

		return jo;
	}

	/**
	 * 校验设备编码唯一
	 */
	@ApiOperation(value = "检测设备编码唯一", tags = "设备管理")
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id, String code) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
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
	public Result<Boolean> checkPropertyCode(String id, String code) {
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
	public Result<Boolean> checkFunctionCode(String id, String code) {
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
		mv.addObject(NormalExcelConstants.CLASS, Device.class);
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
}
