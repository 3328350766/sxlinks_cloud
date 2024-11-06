package com.sxlinks.modules.api.controller.miniProgram.productCenter.broadcast;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.productCenter.broadcast.BroadcastCallRecord;
import com.sxlinks.modules.system.service.productCenter.broadcast.IBroadcastCallRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广播呼叫记录表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/miniProgram/productCenter/broadcastCallRecord")
@Slf4j
@Api(value = "产品中心", tags = {"广播呼叫记录管理"})
public class MiniBroadcastCallRecordController {
	@Autowired
	private IBroadcastCallRecordService broadcastCallRecordService;

	/**
	 * 分页列表查询
	 * @param broadcastCallRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取广播呼叫记录分页列表", tags = "广播呼叫记录管理")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<BroadcastCallRecord>> queryPageList(BroadcastCallRecord broadcastCallRecord,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<BroadcastCallRecord>> result = new Result<IPage<BroadcastCallRecord>>();
		QueryWrapper<BroadcastCallRecord> queryWrapper = QueryGenerator.initQueryWrapper(broadcastCallRecord, req.getParameterMap());
		//非管理员情况下，只能查看当前用户数据
		if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
		Page<BroadcastCallRecord> page = new Page<BroadcastCallRecord>(pageNo, pageSize);
		IPage<BroadcastCallRecord> pageList = broadcastCallRecordService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}


	/**
	 *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "删除广播呼叫记录", tags = "广播呼叫记录管理")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		broadcastCallRecordService.removeById(id);
		return Result.ok("删除角色成功");
	}

	/**
	 *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	@ApiOperation(value = "批量删除广播呼叫记录", tags = "广播呼叫记录管理")
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<BroadcastCallRecord> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<BroadcastCallRecord> result = new Result<BroadcastCallRecord>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			broadcastCallRecordService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除角色成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询广播呼叫记录", tags = "广播呼叫记录管理")
	@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<BroadcastCallRecord> queryById(@RequestParam(name="id",required=true) String id) {
		Result<BroadcastCallRecord> result = new Result<BroadcastCallRecord>();
		BroadcastCallRecord sysbroadcastCallRecord = broadcastCallRecordService.getById(id);
		if(sysbroadcastCallRecord==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysbroadcastCallRecord);
			result.setSuccess(true);
		}
		return result;
	}
	@ApiOperation(value = "查询所有广播呼叫记录", tags = "广播呼叫记录管理")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public Result<List<BroadcastCallRecord>> queryall() {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<List<BroadcastCallRecord>> result = new Result<>();
		List<BroadcastCallRecord> list = broadcastCallRecordService.list(new QueryWrapper<BroadcastCallRecord>().eq("create_by",user.getId()));
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

//	/**
//	 * 校验角色编码唯一
//	 */
//	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
//	public Result<Boolean> checkUsername(String id,String code) {
//		Result<Boolean> result = new Result<>();
//		result.setResult(true);//如果此参数为false则程序发生异常
//		log.info("--验证角色编码是否唯一---id:"+id+"--broadcastCallRecordCode:"+code);
//		try {
//			BroadcastCallRecord broadcastCallRecord = null;
//			if(oConvertUtils.isNotEmpty(id)) {
//				broadcastCallRecord = broadcastCallRecordService.getById(id);
//			}
//			BroadcastCallRecord newRole = broadcastCallRecordService.getOne(new QueryWrapper<BroadcastCallRecord>().lambda().eq(BroadcastCallRecord::getPointCode, code));
//			if(newRole!=null) {
//				//如果根据传入的broadcastCallRecordCode查询到信息了，那么就需要做校验了。
//				if(broadcastCallRecord==null) {
//					//broadcastCallRecord为空=>新增模式=>只要broadcastCallRecordCode存在则返回false
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
	public ModelAndView exportXls(BroadcastCallRecord sysRole,HttpServletRequest request) {

		// Step.1 组装查询条件
		QueryWrapper<BroadcastCallRecord> queryWrapper = QueryGenerator.initQueryWrapper(sysRole, request.getParameterMap());
		//Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<BroadcastCallRecord> pageList = broadcastCallRecordService.list(queryWrapper);
		//导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME,"角色列表");
		mv.addObject(NormalExcelConstants.CLASS,BroadcastCallRecord.class);
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
				//return broadcastCallRecordService.importExcelCheckRoleCode(file, params);
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
