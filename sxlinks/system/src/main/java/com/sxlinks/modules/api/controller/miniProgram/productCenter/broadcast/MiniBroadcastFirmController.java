package com.sxlinks.modules.api.controller.miniProgram.productCenter.broadcast;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastFirm;
import com.sxlinks.modules.system.service.productCenter.broadcast.IBroadcastFirmService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广播厂商表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/productCenter/broadcastFirm")
@Slf4j
@Api(value = "产品中心", tags = {"广播厂商管理"})
public class MiniBroadcastFirmController {
	@Autowired
	private IBroadcastFirmService broadcastFirmService;

	/**
	 * 分页列表查询
	 * @param broadcastFirm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取广播厂商分页列表", tags = "广播厂商管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<BroadcastFirm>> queryPageList(BroadcastFirm broadcastFirm,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<BroadcastFirm>> result = new Result<IPage<BroadcastFirm>>();
		QueryWrapper<BroadcastFirm> queryWrapper = QueryGenerator.initQueryWrapper(broadcastFirm, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<BroadcastFirm> page = new Page<BroadcastFirm>(pageNo, pageSize);
		IPage<BroadcastFirm> pageList = broadcastFirmService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 *   添加
	 * @param broadcastFirm
	 * @return
	 */
	@ApiOperation(value = "添加广播厂商", tags = "广播厂商管理")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<BroadcastFirm> add(@RequestBody BroadcastFirm broadcastFirm) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<BroadcastFirm> result = new Result<BroadcastFirm>();
		try {
			if(StringUtils.isEmpty(broadcastFirm.getCode())){
				result.error500("操作失败,编码不能为空!");
			}else {
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("code", broadcastFirm.getCode());
				BroadcastFirm d = broadcastFirmService.getOne(queryWrapper);
				if (d != null) {
					result.error500("操作失败,编码已经存在");
				} else {
					broadcastFirm.setCreateBy(user.getId());
					broadcastFirm.setCreateTime(new Date());
					broadcastFirmService.save(broadcastFirm);
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
	 * @param broadcastFirm
	 * @return
	 */
	@ApiOperation(value = "编辑广播厂商", tags = "广播厂商管理")
	//@RequiresRoles({"admin"})
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<BroadcastFirm> edit(@RequestBody BroadcastFirm broadcastFirm) {
		Result<BroadcastFirm> result = new Result<BroadcastFirm>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		BroadcastFirm sysbroadcastFirm = broadcastFirmService.getById(broadcastFirm.getId());
		broadcastFirm.setModifyBy(user.getId());
		broadcastFirm.setModifyTime(new Date());
		if(sysbroadcastFirm==null) {
			result.error500("未找到对应实体");
		}else {
			broadcastFirm.setModifyTime(new Date());
			boolean ok = broadcastFirmService.updateById(broadcastFirm);
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
	@ApiOperation(value = "删除广播厂商", tags = "广播厂商管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		broadcastFirmService.removeById(id);
		return Result.ok("删除网关厂商成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除广播厂商", tags = "广播厂商管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<BroadcastFirm> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<BroadcastFirm> result = new Result<BroadcastFirm>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中网关厂商！");
		}else {
			broadcastFirmService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除网关厂商成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询广播厂商", tags = "广播厂商管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<BroadcastFirm> queryById(@RequestParam(name="id",required=true) String id) {
		Result<BroadcastFirm> result = new Result<BroadcastFirm>();
		BroadcastFirm sysbroadcastFirm = broadcastFirmService.getById(id);
		if(sysbroadcastFirm==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysbroadcastFirm);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有广播厂商", tags = "广播厂商管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<BroadcastFirm>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<BroadcastFirm>> result = new Result<>();
		List<BroadcastFirm> list = broadcastFirmService.list(new QueryWrapper<BroadcastFirm>().eq("create_by",user.getId()));
		if(list==null||list.size()<=0) {
			result.error500("未找到网关厂商信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

//	/**
//	 * 校验网关厂商编码唯一
//	 */
//	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
//	public Result<Boolean> checkUsername(String id,String code) {
//		Result<Boolean> result = new Result<>();
//		result.setResult(true);//如果此参数为false则程序发生异常
//		log.info("--验证网关厂商编码是否唯一---id:"+id+"--broadcastFirmCode:"+code);
//		try {
//			BroadcastFirm broadcastFirm = null;
//			if(oConvertUtils.isNotEmpty(id)) {
//				broadcastFirm = broadcastFirmService.getById(id);
//			}
//			BroadcastFirm newRole = broadcastFirmService.getOne(new QueryWrapper<BroadcastFirm>().lambda().eq(BroadcastFirm::getPointCode, code));
//			if(newRole!=null) {
//				//如果根据传入的broadcastFirmCode查询到信息了，那么就需要做校验了。
//				if(broadcastFirm==null) {
//					//broadcastFirm为空=>新增模式=>只要broadcastFirmCode存在则返回false
//					result.setSuccess(false);
//					result.setMessage("编码已存在");
//					return result;
//				}else if(!id.equals(newRole.getId())) {
//					//否则=>编辑模式=>判断两者ID是否一致-
//					result.setSuccess(false);
//					result.setMessage("编码已存在");
//					return result;
//				}
//			}
//		} catch (Exception e) {
//			result.setSuccess(false);
//			result.setResult(false);
//			result.setMessage(e.getMessage());
//			return result;
//		}
//		result.setSuccess(true);
//		return result;
//	}

	/**
	 * 导出excel
	 * @param request
	 */

	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(BroadcastFirm sysRole,HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<BroadcastFirm> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<BroadcastFirm> pageList = broadcastFirmService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"网关厂商列表");
		mv.addObject(NormalExcelConstants.CLASS,BroadcastFirm.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("网关厂商列表数据","导出人:"+user.getRealname(),"导出信息"));
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
				//return broadcastFirmService.importExcelCheckRoleCode(file, params);
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
