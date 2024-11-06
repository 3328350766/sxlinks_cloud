package com.sxlinks.modules.system.controller.ruleEngine;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.productCenter.ProductProperty;
import com.sxlinks.modules.system.entity.ruleEngine.Alarm;
import com.sxlinks.modules.system.entity.ruleEngine.DataForwardItem;
import com.sxlinks.modules.system.service.productCenter.IDevicePropertyService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import com.sxlinks.modules.system.service.ruleEngine.IDataForwardItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;

import com.sxlinks.modules.system.entity.ruleEngine.DataForward;
import com.sxlinks.modules.system.service.ruleEngine.IDataForwardService;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据转发表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/ruleEngine/dataForward")
@Slf4j
@Api(value = "规则引擎", tags = {"数据转发"})
public class DataForwardController {
	@Autowired
	private IDataForwardService dataForwardService;
	@Autowired
	private IDataForwardItemService dataForwardItemService;

	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyService devicePropertyService;
	@Autowired
	private IProductService productService;
	/**
	 * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取数据转发分页列表", tags = "数据转发")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<DataForward>> queryPageList(DataForward role,
													@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<DataForward>> result = new Result<IPage<DataForward>>();
		QueryWrapper<DataForward> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<DataForward> page = new Page<DataForward>(pageNo, pageSize);
		IPage<DataForward> pageList = dataForwardService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	@ApiOperation(value = "获取子项分页列表", tags = "数据转发")
	@RequestMapping(value = "/listItem", method = RequestMethod.GET)
	public Result<IPage<DataForwardItem>> listItem(DataForwardItem item,
											 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											 HttpServletRequest req) {
		Result<IPage<DataForwardItem>> result = new Result<IPage<DataForwardItem>>();
		QueryWrapper<DataForwardItem> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<DataForwardItem> page = new Page<DataForwardItem>(pageNo, pageSize);
		IPage<DataForwardItem> pageList = dataForwardItemService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @param o
	 * @return
	 */
	@ApiOperation(value = "添加转发", tags = "数据转发")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DataForward> add(@RequestBody DataForward o) {
		Result<DataForward> result = new Result<DataForward>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			o.setCreateBy(user.getId());
			o.setCreateTime(new Date());
			dataForwardService.save(o);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加子项信息", tags = "数据转发")
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DataForwardItem> addItem(@RequestBody DataForwardItem item) {
		Result<DataForwardItem> result = new Result<DataForwardItem>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			DataForward df=dataForwardService.getById(item.getDataForwardId());
			item.setDataForwardCode(df.getCode());
			Device device=deviceService.getById(item.getDeviceId());
			item.setDeviceId(device.getId());
			item.setDeviceName(device.getDeviceName());
			item.setDeviceCode(device.getDeviceCode());
			Product product=productService.getOne(new QueryWrapper<Product>().eq("product_code",device.getProductCode())); //产品
			item.setProductId(product.getId());
			item.setProductName(product.getProductName());
			item.setProductCode(product.getProductCode());
			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			dataForwardItemService.save(item);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 *   复制设备子项
	 * @param item
	 * @return
	 */
	@ApiOperation(value = "复制转发子项信息", tags = "设备管理")
	@RequestMapping(value = "/copyItem", method = RequestMethod.PUT)
	//@RequiresRoles({"admin"})
	public Result<Device> copyItem(@RequestBody DataForwardItem item) {
		Result<Device> result = new Result<Device>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		try{
			DataForwardItem dfi=dataForwardItemService.getById(item.getId());
			DataForwardItem ndfi=new DataForwardItem();
			BeanUtils.copyProperties(dfi,ndfi);
			ndfi.setId(null);
			ndfi.setState("0"); //复制的新信息直接禁用
			Device device=deviceService.getById(item.getDeviceId());
			ndfi.setDeviceId(device.getId());
			ndfi.setDeviceName(device.getDeviceName());
			ndfi.setDeviceCode(device.getDeviceCode());
			Product product=productService.getOne(new QueryWrapper<Product>().eq("product_code",device.getProductCode())); //产品
			ndfi.setProductId(product.getId());
			ndfi.setProductName(product.getProductName());
			ndfi.setProductCode(product.getProductCode());
			ndfi.setType(item.getType());
			ndfi.setHttpUrl(item.getHttpUrl());
			ndfi.setHttpMethod(item.getHttpMethod());
			ndfi.setHttpHeader(item.getHttpHeader());
			ndfi.setHttpBody(item.getHttpBody());
			dataForwardItemService.save(ndfi);

			result.success("复制成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}

		/**
         *  编辑
         * @param role
         * @return
         */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "编辑转发", tags = "数据转发")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<DataForward> edit(@RequestBody DataForward role) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<DataForward> result = new Result<DataForward>();
		DataForward sysrole = dataForwardService.getById(role.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			role.setModifyBy(user.getId());
			role.setModifyTime(new Date());
			boolean ok = dataForwardService.updateById(role);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改子项信息", tags = "数据转发")
	@RequestMapping(value = "/editItem", method = RequestMethod.PUT)
	public Result<DataForwardItem> editItem(@RequestBody DataForwardItem item) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<DataForwardItem> result = new Result<DataForwardItem>();
		DataForwardItem dfi = dataForwardItemService.getById(item.getId());

		Device device=deviceService.getById(dfi.getDeviceId());
		item.setDeviceId(device.getId());
		item.setDeviceName(device.getDeviceName());
		item.setDeviceCode(device.getDeviceCode());
		Product product=productService.getOne(new QueryWrapper<Product>().eq("product_code",device.getProductCode())); //产品
		item.setProductId(product.getId());
		item.setProductName(product.getProductName());
		item.setProductCode(product.getProductCode());
		item.setModifyBy(user.getId());
		item.setModifyTime(new Date());
		if(dfi==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = dataForwardItemService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除子项信息", tags = "数据转发")
	@RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
	public Result<?> deleteItem(@RequestParam(name="propertyId",required=true) String itemId) {
		dataForwardItemService.removeById(itemId);
		return Result.ok("删除成功");
	}
	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除转发", tags = "数据转发")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dataForwardItemService.remove(new QueryWrapper<DataForwardItem>().eq("data_forward_id",id));
		dataForwardService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除转发", tags = "数据转发")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<DataForward> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DataForward> result = new Result<DataForward>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中数据转发！");
		}else {
			dataForwardService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除数据转发成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询转发", tags = "数据转发")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<DataForward> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DataForward> result = new Result<DataForward>();
		DataForward sysrole = dataForwardService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有转发", tags = "数据转发")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<DataForward>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<DataForward>> result = new Result<>();
		List<DataForward> list = dataForwardService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到数据转发信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验数据转发编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证数据转发编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			DataForward role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = dataForwardService.getById(id);
			}
			DataForward newRole = dataForwardService.getOne(new QueryWrapper<DataForward>().lambda().eq(DataForward::getCode, code));
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
	public ModelAndView exportXls(DataForward sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<DataForward> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<DataForward> pageList = dataForwardService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"数据转发列表");
		mv.addObject(NormalExcelConstants.CLASS,DataForward.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("数据转发列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return dataForwardService.importExcelCheckRoleCode(file, params);
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
