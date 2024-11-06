package com.sxlinks.modules.api.controller.gateway;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.api.controller.gateway.vo.CloudProduct;
import com.sxlinks.modules.system.controller.productCenter.vo.GatewayVO;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.productCenter.ProductFunction;
import com.sxlinks.modules.system.entity.productCenter.ProductProperty;
import com.sxlinks.modules.system.service.productCenter.IProductFunctionService;
import com.sxlinks.modules.system.service.productCenter.IProductPropertyService;
import com.sxlinks.modules.system.service.productCenter.IProductService;
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
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 网关产品表 前端控制器
 * </p>
 * //网关产品由产品下发
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/gateway/api/product")
@Slf4j
@Api(value = "网关产品", tags = {"产品管理"})
public class GatewayProductController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductPropertyService productPropertyService;
	@Autowired
	private IProductFunctionService productFunctionService;



	/**
	 * 分页列表查询
	 * @param product
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取产品分页列表", tags = "产品管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<CloudProduct>> queryPageList(Product product,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		Result<IPage<CloudProduct>> result = new Result<IPage<CloudProduct>>();
		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(product, req.getParameterMap());
		queryWrapper.eq("del_flag",0); //正常状态
		queryWrapper.eq("node_type","SUB"); //子设备
		Page<Product> page = new Page<Product>(pageNo, pageSize);
		IPage<Product> pageList = productService.page(page, queryWrapper);

		//将功能和属性封装在产品列表中
		IPage<CloudProduct> pageList1=new Page<CloudProduct>();
		BeanUtils.copyProperties(pageList,pageList1);
		List<CloudProduct> lsNewData=new ArrayList();
		List<Product> lsDevice=pageList.getRecords();
		for(int i=0;i<lsDevice.size();i++){
			Product od=(Product)lsDevice.get(i);
			CloudProduct gv=new CloudProduct();
			BeanUtils.copyProperties(od,gv);
			gv.setLsProperty(productPropertyService.list(new QueryWrapper<ProductProperty>().eq("product_code",od.getProductCode()).eq("del_flag",0)));
			gv.setLsFunction(productFunctionService.list(new QueryWrapper<ProductFunction>().eq("product_code",od.getProductCode()).eq("del_flag",0)));
			lsNewData.add(gv);
		}

		pageList1.setRecords(lsNewData);

		result.setSuccess(true);
		result.setResult(pageList1);
		return result;
	}

	@ApiOperation(value = "获取产品属性分页列表", tags = "产品管理")
	@RequestMapping(value = "/listProperty", method = RequestMethod.GET)
	public Result<IPage<ProductProperty>> queryPropertyPageList(ProductProperty property,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		Result<IPage<ProductProperty>> result = new Result<IPage<ProductProperty>>();
		QueryWrapper<ProductProperty> queryWrapper = QueryGenerator.initQueryWrapper(property, req.getParameterMap());
		Page<ProductProperty> page = new Page<ProductProperty>(pageNo, pageSize);
		IPage<ProductProperty> pageList = productPropertyService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ApiOperation(value = "获取产品功能分页列表", tags = "产品管理")
	@RequestMapping(value = "/listFunction", method = RequestMethod.GET)
	public Result<IPage<ProductFunction>> queryFunctionList(ProductFunction function,
															@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															HttpServletRequest req) {
		Result<IPage<ProductFunction>> result = new Result<IPage<ProductFunction>>();
		QueryWrapper<ProductFunction> queryWrapper = QueryGenerator.initQueryWrapper(function, req.getParameterMap());
		Page<ProductFunction> page = new Page<ProductFunction>(pageNo, pageSize);
		IPage<ProductFunction> pageList = productFunctionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @param product
	 * @return
	 */
	@ApiOperation(value = "添加产品信息", tags = "产品管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Product> add(@RequestBody CloudProduct product) {
		Result<Product> result = new Result<Product>();
		try {
			Product o=productService.getOne(new QueryWrapper<Product>().eq("product_code",product.getProductCode()));
			if(o==null) {//对象不存在则添加
				product.setCreateTime(new Date());
				Product nd=new Product();
				BeanUtils.copyProperties(product,nd);
				nd.setCreateTime(new Date());
				productService.save(nd);
				if(product.getLsProperty().size()>0)productFunctionService.saveBatch(product.getLsProperty());
				if(product.getLsFunction().size()>0)productFunctionService.saveBatch(product.getLsFunction());
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
	@ApiOperation(value = "批量添加产品信息", tags = "产品管理")
	@RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Product> batchAdd(@RequestBody List<CloudProduct> lsData) {
		Result<Product> result = new Result<Product>();
		try {
			for(int i=0;i<lsData.size();i++){
				CloudProduct product=(CloudProduct)lsData.get(i);
				Product o=productService.getOne(new QueryWrapper<Product>().eq("product_code",product.getProductCode()));
				if(o==null) {//对象不存在则添加
					product.setCreateTime(new Date());
					Product nd=new Product();
					BeanUtils.copyProperties(product,nd);
					productService.save(nd);
					if(product.getLsProperty().size()>0)productFunctionService.saveBatch(product.getLsProperty());
					if(product.getLsFunction().size()>0)productFunctionService.saveBatch(product.getLsFunction());

//					result.success("添加成功！");
				}else{
//					result.setResult(o);
//					result.error500("操作失败!对象已存在!");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加产品属性信息", tags = "产品管理")
	@RequestMapping(value = "/addProperty", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<ProductProperty> addProperty(@RequestBody ProductProperty property) {
		Result<ProductProperty> result = new Result<ProductProperty>();
		try {
			ProductProperty o=productPropertyService.getOne(new QueryWrapper<ProductProperty>().eq("identifier",property.getIdentifier()));
			if(o==null) {//对象不存在则添加
				property.setCreateTime(new Date());
				productPropertyService.save(property);
				result.success("添加成功！");
				result.setResult(property);
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
	@ApiOperation(value = "添加产品功能信息", tags = "产品管理")
	@RequestMapping(value = "/addFunction", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<ProductFunction> addFunction(@RequestBody ProductFunction function) {
		Result<ProductFunction> result = new Result<ProductFunction>();
		try {
			ProductFunction o=productFunctionService.getOne(new QueryWrapper<ProductFunction>().eq("identifier",function.getIdentifier()));
			if(o==null) {//对象不存在则添加
				function.setCreateTime(new Date());
				productFunctionService.save(function);
				result.setResult(function);
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
	/**
	 *  编辑
	 * @param product
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "修改产品信息", tags = "产品管理")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Product> edit(@RequestBody Product product) {
		Result<Product> result = new Result<Product>();
		Product p = productService.getById(product.getId());
		if(p==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = productService.updateById(product);
			//TODO 返回false说明什么？
			if(ok) {
				result.setResult(product);
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改产品属性信息", tags = "产品管理")
	@RequestMapping(value = "/editProperty", method = RequestMethod.PUT)
	public Result<ProductProperty> editProperty(@RequestBody ProductProperty property) {
		Result<ProductProperty> result = new Result<ProductProperty>();
		ProductProperty sysrole = productPropertyService.getById(property.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = productPropertyService.updateById(property);
			//TODO 返回false说明什么？
			if(ok) {
				result.setResult(sysrole);
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改产品功能信息", tags = "产品管理")
	@RequestMapping(value = "/editFunction", method = RequestMethod.PUT)
	public Result<ProductFunction> editFunction(@RequestBody ProductFunction function) {
		Result<ProductFunction> result = new Result<ProductFunction>();
		ProductFunction sysrole = productFunctionService.getById(function.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = productFunctionService.updateById(function);
			//TODO 返回false说明什么？
			if(ok) {
				result.setResult(sysrole);
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
	@ApiOperation(value = "删除产品信息", tags = "产品管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		//productService.removeById(id);
		Product p = productService.getById(id);
		p.setDelFlag(1); //软删除
		productService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除属性信息", tags = "产品管理")
	@RequestMapping(value = "/deleteProperty", method = RequestMethod.DELETE)
	public Result<?> deleteProperty(@RequestParam(name="propertyId",required=true) String propertyId) {
		//productService.removeById(id);
		ProductProperty p = productPropertyService.getById(propertyId);
		p.setDelFlag(1); //软删除
		productPropertyService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "删除功能信息", tags = "产品管理")
	@RequestMapping(value = "/deleteFunction", method = RequestMethod.DELETE)
	public Result<?> deleteFunction(@RequestParam(name="functionId",required=true) String functionId) {
		//productService.removeById(id);
		ProductFunction p = productFunctionService.getById(functionId);
		p.setDelFlag(1); //软删除
		productFunctionService.saveOrUpdate(p);
		return Result.ok("删除成功");
	}
	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除产品信息", tags = "产品管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Product> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Product> result = new Result<Product>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			productService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除角色成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询产品信息", tags = "产品管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Product> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Product> result = new Result<Product>();
		Product product = productService.getById(id);
		if(product==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(product);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有产品信息", tags = "产品管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Product>> queryall() {
		Result<List<Product>> result = new Result<>();
		List<Product> list = productService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到产品信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有产品属性信息", tags = "产品管理")
	@RequestMapping(value = "/queryAllProperty", method = RequestMethod.GET)
	public Result<List<ProductProperty>> queryallProperty(@RequestParam(name="productCode",required=true) String productCode) {
		Result<List<ProductProperty>> result = new Result<>();
		QueryWrapper<ProductProperty> queryWrapper = new QueryWrapper<ProductProperty>();
		queryWrapper.eq("productCode",productCode);
		List<ProductProperty> list = productPropertyService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到产品属性信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有产品功能信息", tags = "产品管理")
	@RequestMapping(value = "/queryAllFunction", method = RequestMethod.GET)
	public Result<List<ProductFunction>> queryallFunction(@RequestParam(name="productCode",required=true) String productCode) {
		Result<List<ProductFunction>> result = new Result<>();
		QueryWrapper<ProductFunction> queryWrapper = new QueryWrapper<ProductFunction>();
		queryWrapper.eq("productCode",productCode);
		List<ProductFunction> list = productFunctionService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到产品功能信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 校验产品编码唯一
	 */
	@ApiOperation(value = "检测产品编码唯一", tags = "产品管理")
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证产品编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			Product role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = productService.getById(id);
			}
			Product newRole = productService.getOne(new QueryWrapper<Product>().lambda().eq(Product::getProductCode, code));
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
	@ApiOperation(value = "检测产品属性编码唯一", tags = "产品管理")
	@RequestMapping(value = "/checkPropertyCode", method = RequestMethod.GET)
	public Result<Boolean> checkPropertyCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证产品属性编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			ProductProperty role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = productPropertyService.getById(id);
			}
			ProductProperty newRole = productPropertyService.getOne(new QueryWrapper<ProductProperty>().lambda().eq(ProductProperty::getIdentifier, code));
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

	@ApiOperation(value = "检测产品功能编码唯一", tags = "产品管理")
	@RequestMapping(value = "/checkFunctionCode", method = RequestMethod.GET)
	public Result<Boolean> checkFunctionCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证产品功能编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			ProductFunction role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = productFunctionService.getById(id);
			}
			ProductFunction newRole = productFunctionService.getOne(new QueryWrapper<ProductFunction>().lambda().eq(ProductFunction::getIdentifier, code));
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
	//@ApiOperation(value = "导出excel产品信息", tags = "产品管理")
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Product sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Product> pageList = productService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,Product.class);
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
	@ApiOperation(value = "导出excel产品信息", tags = "产品管理")
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
				//return productService.importExcelCheckRoleCode(file, params);
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
