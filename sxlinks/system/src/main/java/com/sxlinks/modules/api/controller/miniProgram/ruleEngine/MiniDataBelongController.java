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
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.entity.ruleEngine.BelongProduct;
import com.sxlinks.modules.system.entity.ruleEngine.BelongType;
import com.sxlinks.modules.system.entity.ruleEngine.BelongTypeItem;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import com.sxlinks.modules.system.service.ruleEngine.IBelongProductService;
import com.sxlinks.modules.system.service.ruleEngine.IBelongTypeItemService;
import com.sxlinks.modules.system.service.ruleEngine.IBelongTypeService;
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
 * 数据归类表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/ruleEngine/dataBelong")
@Slf4j
@Api(value = "数据归类引擎", tags = {"数据归类"})
public class MiniDataBelongController {
	@Autowired
	private IBelongTypeService belongTypeService;
	@Autowired
	private IBelongTypeItemService belongTypeItemService;
	@Autowired
	private IBelongProductService belongProductService;
	@Autowired
	private IProductService productService;
	@Resource
	private BaseCommonService baseCommonService;
	/**
	 * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取数据归类分页列表", tags = "数据归类")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<BelongType>> queryPageList(BelongType role,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<BelongType>> result = new Result<IPage<BelongType>>();
		QueryWrapper<BelongType> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<BelongType> page = new Page<BelongType>(pageNo, pageSize);
		IPage<BelongType> pageList = belongTypeService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取设备数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}
	@ApiOperation(value = "获取子项分页列表", tags = "数据归类")
	@RequestMapping(value = "/listItem", method = RequestMethod.GET)
	public Result<IPage<BelongTypeItem>> listItem(BelongTypeItem item,
											 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											 HttpServletRequest req) {
		Result<IPage<BelongTypeItem>> result = new Result<IPage<BelongTypeItem>>();
		QueryWrapper<BelongTypeItem> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<BelongTypeItem> page = new Page<BelongTypeItem>(pageNo, pageSize);
		IPage<BelongTypeItem> pageList = belongTypeItemService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	@ApiOperation(value = "获取归类产品分页列表", tags = "数据归类")
	@RequestMapping(value = "/listBelongProduct", method = RequestMethod.GET)
	public Result<IPage<BelongProduct>> listBelongProduct(BelongProduct item,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req) {
		Result<IPage<BelongProduct>> result = new Result<IPage<BelongProduct>>();
		QueryWrapper<BelongProduct> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<BelongProduct> page = new Page<BelongProduct>(pageNo, pageSize);
		IPage<BelongProduct> pageList = belongProductService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @param o
	 * @return
	 */
	@ApiOperation(value = "添加数据归类", tags = "数据归类")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<BelongType> add(@RequestBody BelongType o) {
		Result<BelongType> result = new Result<BelongType>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			o.setCreateBy(user.getId());
			o.setCreateTime(new Date());
			belongTypeService.save(o);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加子项信息", tags = "数据归类")
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<BelongTypeItem> addItem(@RequestBody BelongTypeItem item) {
		Result<BelongTypeItem> result = new Result<BelongTypeItem>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			BelongType belongType=belongTypeService.getById(item.getBelongId());
			item.setBelongCode(belongType.getCode());
			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			belongTypeItemService.save(item);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加归类产品", tags = "数据归类")
	@RequestMapping(value = "/addBelongProduct", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<BelongProduct> addBelongProduct(@RequestBody BelongProduct item) {
		Result<BelongProduct> result = new Result<BelongProduct>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			BelongType belongType=belongTypeService.getById(item.getBelongId());
			item.setBelongCode(belongType.getCode());
			item.setBelongName(belongType.getName());
			Product product=productService.getById(item.getProductId());
			item.setName(product.getProductName());
			item.setProductCode(product.getProductCode());

			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			belongProductService.save(item);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  编辑
	 * @param item
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "编辑数据归类", tags = "数据归类")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<BelongType> edit(@RequestBody BelongType item) {
		Result<BelongType> result = new Result<BelongType>();
		BelongType sysrole = belongTypeService.getById(item.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {

			item.setModifyTime(new Date());
			boolean ok = belongTypeService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改子项信息", tags = "数据归类")
	@RequestMapping(value = "/editItem", method = RequestMethod.PUT)
	public Result<BelongTypeItem> editItem(@RequestBody BelongTypeItem item) {
		Result<BelongTypeItem> result = new Result<BelongTypeItem>();
		BelongTypeItem sysrole = belongTypeItemService.getById(item.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			BelongType belongType=belongTypeService.getById(item.getBelongId());
			item.setBelongCode(belongType.getCode());
			item.setModifyTime(new Date());
			boolean ok = belongTypeItemService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除子项信息", tags = "数据归类")
	@RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
	public Result<?> deleteItem(@RequestParam(name="propertyId",required=true) String itemId) {
		belongTypeItemService.removeById(itemId);
		return Result.ok("删除成功");
	}
	@ApiOperation(value = "修改归类产品信息", tags = "数据归类")
	@RequestMapping(value = "/editBelongProduct", method = RequestMethod.PUT)
	public Result<BelongProduct> editBelongProduct(@RequestBody BelongProduct item) {
		Result<BelongProduct> result = new Result<BelongProduct>();
		BelongProduct sysrole = belongProductService.getById(item.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			BelongType belongType=belongTypeService.getById(item.getBelongId());
			item.setBelongCode(belongType.getCode());
			item.setBelongName(belongType.getName());
			Product product=productService.getById(item.getProductId());
			item.setName(product.getProductName());
			item.setProductCode(product.getProductCode());
			item.setModifyTime(new Date());
			boolean ok = belongProductService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除归类产品信息", tags = "数据归类")
	@RequestMapping(value = "/deleteBelongProduct", method = RequestMethod.DELETE)
	public Result<?> deleteBelongProduct(@RequestParam(name="propertyId",required=true) String itemId) {
		belongProductService.removeById(itemId);
		return Result.ok("删除成功");
	}
	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除数据归类", tags = "数据归类")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		belongTypeService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除数据归类", tags = "数据归类")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<BelongType> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<BelongType> result = new Result<BelongType>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中数据归类！");
		}else {
			belongTypeService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除数据归类成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询数据归类", tags = "数据归类")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<BelongType> queryById(@RequestParam(name="id",required=true) String id) {
		Result<BelongType> result = new Result<BelongType>();
		BelongType sysrole = belongTypeService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有数据归类", tags = "数据归类")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<BelongType>> queryall() {
		Result<List<BelongType>> result = new Result<>();
		List<BelongType> list = belongTypeService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到数据归类信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验数据归类编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证数据归类编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			BelongType role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = belongTypeService.getById(id);
			}
			BelongType newRole = belongTypeService.getOne(new QueryWrapper<BelongType>().lambda().eq(BelongType::getCode, code));
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
	public ModelAndView exportXls(BelongType sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<BelongType> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<BelongType> pageList = belongTypeService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"数据归类列表");
		mv.addObject(NormalExcelConstants.CLASS, BelongType.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("数据归类列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return belongTypeService.importExcelCheckRoleCode(file, params);
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
