package com.sxlinks.modules.api.controller.miniProgram.productCenter;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.api.controller.miniProgram.productCenter.vo.GatewayVO;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Gateway;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IGatewayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
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
@RequestMapping("/miniProgram/productCenter/gateway")
@Slf4j
@Api(value = "产品中心", tags = {"网关管理"})
public class MiniGatewayController {
	@Autowired
	private IGatewayService gatewayService;
	@Autowired
	private IDeviceService deviceService;
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
	@ApiOperation(value = "获取网关分页列表", tags = "网关管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<GatewayVO>> queryPageList(Device device,
                                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                  HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<GatewayVO>> result = new Result<IPage<GatewayVO>>();
		device.setDelFlag(0); //正常可用的
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
        queryWrapper.eq("del_flag",0);
        queryWrapper.eq("node_type","GATEWAY"); //节点类型 直连设备DIRECT/子设备SUB/网关设备GATEWAY
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
        Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);

		IPage<GatewayVO> pageList1=new Page<GatewayVO>();
		BeanUtils.copyProperties(pageList,pageList1);
		List<GatewayVO> lsNewData=new ArrayList();
		List<Device> lsDevice=pageList.getRecords();
		for(int i=0;i<lsDevice.size();i++){
			Device od=(Device)lsDevice.get(i);
			GatewayVO gv=new GatewayVO();
			BeanUtils.copyProperties(od,gv);
			gv.setLsSubDevice(deviceService.list(new QueryWrapper<Device>().eq("gw_dev_code",od.getDeviceCode()).eq("del_flag","0")));
			lsNewData.add(gv);
		}
		pageList1.setRecords(lsNewData);
		result.setSuccess(true);
		result.setResult(pageList1);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取网关数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	@ApiOperation(value = "获取网关的子设备分页列表", tags = "网关管理")
	@RequestMapping(value = "/listSubDevice", method = RequestMethod.GET)
	public Result<IPage<Device>> querySubDevicePageList(@RequestParam(name="deviceCode", defaultValue="")String deviceCode,
														@RequestParam(name="deviceName", defaultValue="")String deviceName,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<Device>> result = new Result<IPage<Device>>();
		QueryWrapper<Device> queryWrapper = new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);
		queryWrapper.eq("gw_dev_code",deviceCode); //网关编码

		if(StringUtils.isNotEmpty(deviceName)){
			queryWrapper.like("deviceName","%"+deviceName+"%"); //网关编码
		}
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @return
	 */
	@ApiOperation(value = "绑定子设备", tags = "网关管理")
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Device> bind(@RequestParam(name="deviceCode", defaultValue="1")String deviceCode,
							   @RequestParam(name="subDeviceCode", defaultValue="1")String subDeviceCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Device> result = new Result<Device>();
		try {
			QueryWrapper queryWrapper=new QueryWrapper();
			queryWrapper.eq("del_flag",0);
			queryWrapper.eq("device_code",subDeviceCode); //根据设备编码查找
			queryWrapper.eq("create_by",user.getId());
			Device d = deviceService.getOne(queryWrapper);
			if(d.getGwDevCode()==null ||d.getGwDevCode().equals("")) {
				d.setGwDevCode(deviceCode);
				deviceService.updateById(d);
				result.success("绑定成功！");
			}else{
				result.error500("此设备已绑定网关");
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
	@ApiOperation(value = "解绑子设备", tags = "网关管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	public Result<?> unbind(@RequestParam(name="deviceCode", defaultValue="")String deviceCode,
							@RequestParam(name="subDeviceCode", defaultValue="")String subDeviceCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Device> result = new Result<Device>();
		try {
			QueryWrapper queryWrapper=new QueryWrapper();
			queryWrapper.eq("del_flag",0);
			queryWrapper.eq("device_code",subDeviceCode); //根据设备编码查找
			queryWrapper.eq("gw_dev_code",deviceCode);
			Device d = deviceService.getOne(queryWrapper);
			if(d.getGwDevCode()!=null ||!d.getGwDevCode().equals("")) {
				d.setGwDevCode(""); //将子设备的网关编码清空
				deviceService.updateById(d);
				result.success("解绑成功！");
			}else{
				result.success("解绑失败！此设备没有绑定网关");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
    @ApiOperation(value = "通过id查询", tags = "网关管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Device> queryById(@RequestParam(name="id",required=true) String id) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Device> result = new Result<Device>();
        Device d = deviceService.getById(id);
		if(d==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(d);
			result.setSuccess(true);
		}
		return result;
	}
    @ApiOperation(value = "查询所有网关设备", tags = "网关管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<GatewayVO>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<GatewayVO>> result = new Result<>();
		QueryWrapper<Device> queryWrapper=new QueryWrapper<Device>();
        queryWrapper.eq("del_flag",0);//正常可用
        queryWrapper.eq("node_type","GATEWAY"); //节点类型 直连设备DIRECT/子设备SUB/网关设备GATEWAY
		queryWrapper.eq("create_by",user.getId());
        List<Device> list = deviceService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到网关信息");
		}else {
			List<GatewayVO> lsNewData=new ArrayList();
			List<Device> lsDevice=list;
			for(int i=0;i<lsDevice.size();i++){
				Device od=(Device)lsDevice.get(i);
				GatewayVO gv=new GatewayVO();
				BeanUtils.copyProperties(od,gv);
				gv.setLsSubDevice(deviceService.list(new QueryWrapper<Device>().eq("gw_dev_code",od.getDeviceCode()).eq("del_flag","0")));
				lsNewData.add(gv);
			}
			result.setResult(lsNewData);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询网关下的所有子设备", tags = "网关管理")
	@RequestMapping(value = "/queryAllSubDevice", method = RequestMethod.GET)
	public Result<List<Device>> queryAllSubDevice(@RequestParam(name="deviceCode", defaultValue="")String deviceCode) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Device>> result = new Result<>();
		QueryWrapper<Device> queryWrapper=new QueryWrapper<Device>();
		queryWrapper.eq("del_flag",0);//正常可用
		queryWrapper.eq("gw_dev_code",deviceCode); //网关编码
        queryWrapper.eq("node_type","SUB"); //节点编码
		queryWrapper.eq("create_by",user.getId());
		List<Device> list = deviceService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到子设备");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
    @ApiOperation(value = "查询未经绑定的所有子设备", tags = "网关管理")
    @RequestMapping(value = "/queryAllSubDeviceNoBind", method = RequestMethod.GET)
    public Result<List<Device>> queryAllSubDeviceNoBind() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Result<List<Device>> result = new Result<>();
        QueryWrapper<Device> queryWrapper=new QueryWrapper<Device>();
        queryWrapper.eq("del_flag",0);//正常可用
        queryWrapper.eq("node_type","SUB"); //子设备节点
        queryWrapper.eq("gw_dev_code",""); //网关编码
		queryWrapper.eq("create_by",user.getId());
        List<Device> list = deviceService.list(queryWrapper);
        if(list==null||list.size()<=0) {
            result.error500("未找到子设备");
        }else {
            result.setResult(list);
            result.setSuccess(true);
        }
        return result;
    }

	/**
	 * 导出excel
	 * @param request
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Gateway sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Gateway> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Gateway> pageList = gatewayService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,Gateway.class);
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
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//return gatewayService.importExcelCheckRoleCode(file, params);
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
