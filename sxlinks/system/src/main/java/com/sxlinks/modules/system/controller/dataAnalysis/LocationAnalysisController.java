package com.sxlinks.modules.system.controller.dataAnalysis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.modules.system.entity.productCenter.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.service.productCenter.IProductService;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
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
 * 区域分析表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dataAnalysis/locationAnalysis")
@Slf4j
public class LocationAnalysisController {
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
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Product>> queryPageList(Product role,
												@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												HttpServletRequest req) {
		Result<IPage<Product>> result = new Result<IPage<Product>>();
		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		Page<Product> page = new Page<Product>(pageNo, pageSize);
		IPage<Product> pageList = productService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 *   添加
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Product> add(@RequestBody Product role) {
		Result<Product> result = new Result<Product>();
		try {
			role.setCreateTime(new Date());
			productService.save(role);
			result.success("添加成功！");
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
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Product> edit(@RequestBody Product role) {
		Result<Product> result = new Result<Product>();
		Product sysrole = productService.getById(role.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			//role.setModifyTime(new Date());
			boolean ok = productService.updateById(role);
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
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		productService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Product> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Product> result = new Result<Product>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中区域分析！");
		}else {
			productService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除区域分析成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Product> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Product> result = new Result<Product>();
		Product sysrole = productService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Product>> queryall() {
		Result<List<Product>> result = new Result<>();
		List<Product> list = productService.list();
		if(list==null||list.size()<=0) {
			result.error500("未找到区域分析信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验区域分析编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证区域分析编码是否唯一---id:"+id+"--roleCode:"+code);
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

	/**
	 * 导出excel
	 * @param request
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(Product sysRole,HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Product> pageList = productService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"区域分析列表");
		mv.addObject(NormalExcelConstants.CLASS,Product.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("区域分析列表数据","导出人:"+user.getRealname(),"导出信息"));
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
