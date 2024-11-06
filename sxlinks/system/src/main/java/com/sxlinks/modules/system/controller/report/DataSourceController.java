package com.sxlinks.modules.system.controller.report;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.controller.report.param.ConnectionParam;
import com.sxlinks.modules.system.entity.report.DataSource;
import com.sxlinks.modules.system.entity.report.DataSourceDto;
import com.sxlinks.modules.system.service.report.DataSourceService;
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
 * 数据源 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2023-02-26
 */
@RestController
@RequestMapping("/dataSource")
@Slf4j
@Api(value = "数据源", tags = {"数据源管理"})
public class DataSourceController {
	@Autowired
	private DataSourceService dataSourceService;
	@Resource
	private BaseCommonService baseCommonService;

	/**
	 * 分页列表查询
	 * @param dataSource
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取数据源分页列表", tags = "数据源管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<DataSource>> queryPageList(DataSource dataSource,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<DataSource>> result = new Result<IPage<DataSource>>();
		QueryWrapper<DataSource> queryWrapper = QueryGenerator.initQueryWrapper(dataSource, req.getParameterMap());
		queryWrapper.eq("enable_flag","1");//启用标志
		queryWrapper.eq("delete_flag","0");//删除标志
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<DataSource> page = new Page<DataSource>(pageNo, pageSize);
		IPage<DataSource> pageList = dataSourceService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		//日志记录
		baseCommonService.addLog("用户名: " + user.getUsername() + ",获取数据源数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

		return result;
	}

	/**
	 *   添加

	 * @return
	 */
	@ApiOperation(value = "添加数据源", tags = "数据源管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<DataSource> add(@RequestBody DataSourceDto dto) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<DataSource> result = new Result<DataSource>();
		try {
			if(StringUtils.isEmpty(dto.getSourceCode())){
				result.error500("操作失败,编码不能为空!");
			}else {
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("source_code", dto.getSourceCode());
				DataSource d = dataSourceService.getOne(queryWrapper);
				if (d != null) {
					result.error500("操作失败,编码已经存在");
				} else {
					DataSource dataSource=new DataSource();
					dataSource.setSourceName(dto.getSourceName());
					dataSource.setSourceCode(dto.getSourceCode());
					dataSource.setSourceDesc(dto.getSourceDesc());
					dataSource.setSourceType(dto.getSourceType());
					if(dataSource.getSourceType().equals("sql")||dataSource.getSourceType().equals("mysql")||dataSource.getSourceType().equals("oracle")||dataSource.getSourceType().equals("mssqlserver")){
						JSONObject jo=new JSONObject();
						jo.put("jdbcUrl",dto.getJdbcUrl());
						jo.put("username",dto.getJdbcUrl());
						jo.put("password",dto.getJdbcUrl());
						jo.put("driverName",dto.getDriverName());
						dataSource.setSourceConfig(jo.toJSONString());
					}
					if(dataSource.getSourceType().equals("http")){
						JSONObject jo=new JSONObject();
						jo.put("apiUrl",dto.getApiUrl());
						jo.put("method",dto.getMethod());
						jo.put("header",dto.getHeader());
						jo.put("body",dto.getBody());
						dataSource.setSourceConfig(jo.toJSONString());
					}
					dataSource.setEnableFlag(1);
					dataSource.setDeleteFlag(0);
					dataSource.setCreateBy(user.getId());
					dataSource.setCreateTime(new Date());
					dataSourceService.save(dataSource);
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
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "编辑数据源", tags = "数据源管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<DataSource> edit(@RequestBody DataSourceDto dto) {
		Result<DataSource> result = new Result<DataSource>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		DataSource dataSource = dataSourceService.getById(dto.getId());

		if(dataSource==null) {
			result.error500("未找到对应实体");
		}else {

			dataSource.setSourceName(dto.getSourceName());
			dataSource.setSourceCode(dto.getSourceCode());
			dataSource.setSourceDesc(dto.getSourceDesc());
			dataSource.setSourceType(dto.getSourceType());
			if(dataSource.getSourceType().equals("sql")||dataSource.getSourceType().equals("mysql")||dataSource.getSourceType().equals("oracle")||dataSource.getSourceType().equals("mssqlserver")){
				JSONObject jo=new JSONObject();
				jo.put("jdbcUrl",dto.getJdbcUrl());
				jo.put("username",dto.getUsername());
				jo.put("password",dto.getPassword());
				jo.put("driverName",dto.getDriverName());
				dataSource.setSourceConfig(jo.toJSONString());
			}
			if(dataSource.getSourceType().equals("http")){
				JSONObject jo=new JSONObject();
				jo.put("apiUrl",dto.getApiUrl());
				jo.put("method",dto.getMethod());
				jo.put("header",dto.getHeader());
				jo.put("body",dto.getBody());
				dataSource.setSourceConfig(jo.toJSONString());
			}
			dataSource.setEnableFlag(1);
			dataSource.setDeleteFlag(0);
			dataSource.setModifyBy(user.getId());
			dataSource.setModifyTime(new Date());
			boolean ok = dataSourceService.updateById(dataSource);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	@ApiOperation(value = "测试连接", tags = "数据源管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/testConnection", method = RequestMethod.POST)
	public Result<Boolean> testConnection(@RequestBody DataSourceDto dto) {
		Result<Boolean> result = new Result<Boolean>();
		boolean rstr=false;
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		DataSource dataSource = new DataSource();

		if(dto==null||dto.getSourceType()==null||dto.getSourceConfig()==null) {
			result.error500("配置为空，请详细输入");
		}else {

			dataSource.setSourceName(dto.getSourceName());
			dataSource.setSourceCode(dto.getSourceCode());
			dataSource.setSourceDesc(dto.getSourceDesc());
			dataSource.setSourceType(dto.getSourceType());
			if(dataSource.getSourceType().equals("sql")||dataSource.getSourceType().equals("mysql")||dataSource.getSourceType().equals("oracle")||dataSource.getSourceType().equals("mssqlserver")){
				JSONObject jo=new JSONObject();
				jo.put("jdbcUrl",dto.getJdbcUrl());
				jo.put("username",dto.getUsername());
				jo.put("password",dto.getPassword());
				jo.put("driverName",dto.getDriverName());
				dataSource.setSourceConfig(jo.toJSONString());
			}
			if(dataSource.getSourceType().equals("http")){
				JSONObject jo=new JSONObject();
				jo.put("apiUrl",dto.getApiUrl());
				jo.put("method",dto.getMethod());
				jo.put("header",dto.getHeader());
				jo.put("body",dto.getBody());
				dataSource.setSourceConfig(jo.toJSONString());
			}
			ConnectionParam param=new ConnectionParam();
			param.setSourceType(dto.getSourceType());
			param.setSourceConfig(dto.getSourceConfig());
			rstr=dataSourceService.testConnection(param);
			result.setResult(rstr);
			//TODO 返回false说明什么？
			if(rstr) {
				result.success("连接成功!");
			}else{
				result.error500("连接失败!");
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
	@ApiOperation(value = "删除数据源", tags = "数据源管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		DataSource c=dataSourceService.getById(id);
		c.setEnableFlag(0);
		c.setDeleteFlag(1);
		dataSourceService.updateById(c); //软删除
		return Result.ok("删除数据源成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除数据源", tags = "数据源管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<DataSource> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<DataSource> result = new Result<DataSource>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中数据源！");
		}else {
			dataSourceService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除数据源成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询数据源", tags = "数据源管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<DataSourceDto> queryById(@RequestParam(name="id",required=true) String id) {
		Result<DataSourceDto> result = new Result<DataSourceDto>();
		DataSource dataSource = dataSourceService.getById(id);
		if(dataSource==null) {
			result.error500("未找到对应实体");
		}else {
			DataSourceDto dto=new DataSourceDto();
			BeanUtils.copyProperties(dataSource,dto);
			if(StringUtils.isNotEmpty(dataSource.getSourceConfig())){
				if(dataSource.getSourceType().equals("sql")||dataSource.getSourceType().equals("mysql")||dataSource.getSourceType().equals("oracle")||dataSource.getSourceType().equals("mssqlserver")){
					JSONObject jo= JSON.parseObject(dataSource.getSourceConfig());
					dto.setJdbcUrl(jo.getString("jdbcUrl"));
					dto.setUsername(jo.getString("username"));
					dto.setPassword(jo.getString("password"));
					dto.setDriverName(jo.getString("driverName"));
				}
				if(dataSource.getSourceType().equals("http")){
					JSONObject jo= JSON.parseObject(dataSource.getSourceConfig());
					dto.setApiUrl(jo.getString("apiUrl"));
					dto.setMethod(jo.getString("method"));
					dto.setHeader(jo.getString("header"));
					dto.setBody(jo.getString("body"));
				}
			}
			result.setResult(dto);
			result.setSuccess(true);
		}
		return result;
	}

	@ApiOperation(value = "查询所有数据源", tags = "数据源管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<DataSource>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<DataSource>> result = new Result<>();
		List<DataSource> list = dataSourceService.list(new QueryWrapper<DataSource>().eq("create_by",user.getId()));
		if(list==null||list.size()<=0) {
			result.error500("未找到数据源信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验数据源编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkCode(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证数据源编码是否唯一---id:"+id+"--dataSourceCode:"+code);
		try {
			DataSource dataSource = null;
			if(oConvertUtils.isNotEmpty(id)) {
				dataSource = dataSourceService.getById(id);
			}
			DataSource newRole = dataSourceService.getOne(new QueryWrapper<DataSource>().lambda().eq(DataSource::getSourceCode, code));
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
	public ModelAndView exportXls(DataSource sysRole,HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<DataSource> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<DataSource> pageList = dataSourceService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"数据源列表");
		mv.addObject(NormalExcelConstants.CLASS,DataSource.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("数据源列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return dataSourceService.importExcelCheckRoleCode(file, params);
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
