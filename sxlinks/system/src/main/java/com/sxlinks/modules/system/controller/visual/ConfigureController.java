package com.sxlinks.modules.system.controller.visual;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;

import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.entity.visual.Configure;
import com.sxlinks.modules.system.entity.visual.ConfigureContent;
import com.sxlinks.modules.system.service.visual.IConfigureContentService;
import com.sxlinks.modules.system.service.visual.IConfigureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * 组态 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2023-02-26
 */
@RestController
@RequestMapping("/visual/configure")
@Slf4j
@Api(value = "可视化", tags = {"组态管理"})
public class ConfigureController {
	@Autowired
	private IConfigureService configureService;
	@Autowired
	private IConfigureContentService configureContentService;
	@Resource
	private BaseCommonService baseCommonService;
	/**
	 * 分页列表查询
	 * @param configure
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取组态分页列表", tags = "组态管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<Configure>> queryPageList(Configure configure,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<Configure>> result = new Result<IPage<Configure>>();
		QueryWrapper<Configure> queryWrapper = QueryGenerator.initQueryWrapper(configure, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<Configure> page = new Page<Configure>(pageNo, pageSize);
		IPage<Configure> pageList = configureService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取组态数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	/**
	 *   添加
	 * @param configure
	 * @return
	 */
	@ApiOperation(value = "添加组态", tags = "组态管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<Configure> add(@RequestBody Configure configure) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<Configure> result = new Result<Configure>();
		try {
			if(StringUtils.isEmpty(configure.getCode())){
				result.error500("操作失败,编码不能为空!");
			}else {
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("code", configure.getCode());
				Configure d = configureService.getOne(queryWrapper);
				if (d != null) {
					result.error500("操作失败,编码已经存在");
				} else {
					configure.setDelFlag(0);
					configure.setCreateBy(user.getId());
					configure.setCreateTime(new Date());
					configureService.save(configure);
					result.success("添加成功！");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}


	/**
	 *  编辑
	 * @param configure
	 * @return
	 */
	@ApiOperation(value = "编辑组态", tags = "组态管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<Configure> edit(@RequestBody Configure configure) {
		Result<Configure> result = new Result<Configure>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Configure sysconfigure = configureService.getById(configure.getId());
		configure.setModifyBy(user.getId());
		configure.setModifyTime(new Date());
		if(sysconfigure==null) {
			result.error500("未找到对应实体");
		}else {
			configure.setModifyTime(new Date());
			boolean ok = configureService.updateById(configure);
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
	@ApiOperation(value = "删除组态", tags = "组态管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		Configure c=configureService.getById(id);
		//删除组态内容
		configureContentService.remove(new QueryWrapper<ConfigureContent>()
				.lambda().eq(ConfigureContent::getConfigureCode, c.getCode()));
		configureService.removeById(id);
		return Result.ok("删除组态成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除组态", tags = "组态管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<Configure> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Configure> result = new Result<Configure>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中组态！");
		}else {
			configureService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除组态成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询组态", tags = "组态管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<Configure> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Configure> result = new Result<Configure>();
		Configure sysconfigure = configureService.getById(id);
		if(sysconfigure==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysconfigure);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "保存组态内容", tags = "组态管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public Result<ConfigureContent> saveContent(@RequestBody ConfigureContent content) {
		Result<ConfigureContent> result = new Result<ConfigureContent>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		if(content!=null &&content.getConfigureId()!=null) {
			ConfigureContent cc = configureContentService.getOne(new QueryWrapper<ConfigureContent>().eq("configure_id", content.getConfigureId()));
			Configure c=configureService.getOne(new QueryWrapper<Configure>().eq("id",content.getConfigureId()));

			if (cc == null) {
				cc=new ConfigureContent();
				cc.setConfigureId(content.getConfigureId());
				cc.setContent(content.getContent());
				cc.setConfigureCode(c.getCode());
				cc.setCreateBy(user.getId());
				cc.setCreateTime(new Date());
				boolean ok = configureContentService.save(cc);
				//TODO 返回false说明什么？
				if (ok) {
					result.success("保存成功!");
				}
			} else {
				cc.setContent(content.getContent());
				cc.setConfigureCode(c.getCode());
				cc.setModifyBy(user.getId());
				cc.setModifyTime(new Date());
				boolean ok = configureContentService.updateById(cc);
				//TODO 返回false说明什么？
				if (ok) {
					result.success("保存成功!");
				}
			}
		}else{
			result.error500("组态编号不能为空");
		}
		return result;
	}
	@ApiOperation(value = "根据id查询组态内容", tags = "组态管理")
	@RequestMapping(value = "/queryContentById", method = RequestMethod.GET)
	public Result<ConfigureContent> queryContentById(@RequestParam(name="id",required=true) String id) {
		Result<ConfigureContent> result = new Result<ConfigureContent>();
		ConfigureContent sysconfigure = configureContentService.getOne(new QueryWrapper<ConfigureContent>().eq("configure_id",id));
		if(sysconfigure==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysconfigure);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有组态", tags = "组态管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<Configure>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<Configure>> result = new Result<>();
		List<Configure> list = configureService.list(new QueryWrapper<Configure>().eq("create_by",user.getId()));
		if(list==null||list.size()<=0) {
			result.error500("未找到组态信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验组态编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证组态编码是否唯一---id:"+id+"--configureCode:"+code);
		try {
			Configure configure = null;
			if(oConvertUtils.isNotEmpty(id)) {
				configure = configureService.getById(id);
			}
			Configure newRole = configureService.getOne(new QueryWrapper<Configure>().lambda().eq(Configure::getCode, code));
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
	public ModelAndView exportXls(Configure sysRole,HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<Configure> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<Configure> pageList = configureService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"组态列表");
		mv.addObject(NormalExcelConstants.CLASS,Configure.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("组态列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return configureService.importExcelCheckRoleCode(file, params);
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
