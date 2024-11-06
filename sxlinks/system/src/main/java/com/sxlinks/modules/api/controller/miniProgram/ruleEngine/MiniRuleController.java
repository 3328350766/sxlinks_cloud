package com.sxlinks.modules.api.controller.miniProgram.ruleEngine;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.ruleEngine.DeviceRule;
import com.sxlinks.modules.system.entity.ruleEngine.DeviceRuleItem;
import com.sxlinks.modules.system.service.ruleEngine.IRuleItemService;
import com.sxlinks.modules.system.service.ruleEngine.IRuleService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/ruleEngine/rule")
@Slf4j
@Api(value = "规则引擎", tags = {"规则管理"})
public class MiniRuleController {
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IRuleItemService ruleItemService;

	/**
	 * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取规则分页列表", tags = "规则管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<DeviceRule>> queryPageList(DeviceRule role,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<DeviceRule>> result = new Result<IPage<DeviceRule>>();
		QueryWrapper<DeviceRule> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<DeviceRule> page = new Page<DeviceRule>(pageNo, pageSize);
		IPage<DeviceRule> pageList = ruleService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	@ApiOperation(value = "获取子项分页列表", tags = "规则管理")
	@RequestMapping(value = "/listItem", method = RequestMethod.GET)
	public Result<IPage<DeviceRuleItem>> listItem(DeviceRuleItem item,
											 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											 HttpServletRequest req) {
		Result<IPage<DeviceRuleItem>> result = new Result<IPage<DeviceRuleItem>>();
		QueryWrapper<DeviceRuleItem> queryWrapper = QueryGenerator.initQueryWrapper(item, req.getParameterMap());
		Page<DeviceRuleItem> page = new Page<DeviceRuleItem>(pageNo, pageSize);
		IPage<DeviceRuleItem> pageList = ruleItemService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	/**
	 *   添加
	 * @param o
	 * @return
	 */
	@ApiOperation(value = "添加规则", tags = "规则管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DeviceRule> add(@RequestBody DeviceRule o) {
		Result<DeviceRule> result = new Result<DeviceRule>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			o.setCreateBy(user.getId());
			o.setCreateTime(new Date());
			ruleService.save(o);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	@ApiOperation(value = "添加子项信息", tags = "规则管理")
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DeviceRuleItem> addItem(@RequestBody DeviceRuleItem item) {
		Result<DeviceRuleItem> result = new Result<DeviceRuleItem>();
		try {
			LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			item.setCreateBy(user.getId());
			item.setCreateTime(new Date());
			ruleItemService.save(item);
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
	@ApiOperation(value = "编辑规则", tags = "规则管理")
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<DeviceRule> edit(@RequestBody DeviceRule role) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<DeviceRule> result = new Result<DeviceRule>();
		DeviceRule sysrole = ruleService.getById(role.getId());

		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			role.setModifyBy(user.getId());
			role.setModifyTime(new Date());
			boolean ok = ruleService.updateById(role);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "修改子项信息", tags = "规则管理")
	@RequestMapping(value = "/editItem", method = RequestMethod.PUT)
	public Result<DeviceRuleItem> editItem(@RequestBody DeviceRuleItem item) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<DeviceRuleItem> result = new Result<DeviceRuleItem>();
		DeviceRuleItem sysrole = ruleItemService.getById(item.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			item.setModifyBy(user.getId());
			item.setModifyTime(new Date());
			boolean ok = ruleItemService.updateById(item);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}
	@ApiOperation(value = "删除子项信息", tags = "规则管理")
	@RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
	public Result<?> deleteItem(@RequestParam(name="propertyId",required=true) String itemId) {
		ruleItemService.removeById(itemId);
		return Result.ok("删除成功");
	}
	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除规则", tags = "规则管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ruleService.removeById(id);
		return Result.ok("删除成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除规则", tags = "规则管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<DeviceRule> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DeviceRule> result = new Result<DeviceRule>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中规则！");
		}else {
			ruleService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除规则成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询规则", tags = "规则管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<DeviceRule> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DeviceRule> result = new Result<DeviceRule>();
		DeviceRule sysrole = ruleService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有规则", tags = "规则管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<DeviceRule>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<DeviceRule>> result = new Result<>();
		List<DeviceRule> list = ruleService.list(new QueryWrapper<DeviceRule>().eq("create_by",user.getId()));

		if(list==null||list.size()<=0) {
			result.error500("未找到规则信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验规则编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证规则编码是否唯一---id:"+id+"--roleCode:"+code);
		try {
			DeviceRule role = null;
			if(oConvertUtils.isNotEmpty(id)) {
				role = ruleService.getById(id);
			}
			DeviceRule newRole = ruleService.getOne(new QueryWrapper<DeviceRule>().lambda().eq(DeviceRule::getCode, code));
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
	public ModelAndView exportXls(DeviceRule sysRole, HttpServletRequest request) {
		// Step.1 组装查询条件
		QueryWrapper<DeviceRule> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<DeviceRule> pageList = ruleService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"规则列表");
		mv.addObject(NormalExcelConstants.CLASS, DeviceRule.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("规则列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return ruleService.importExcelCheckRoleCode(file, params);
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
