package com.sxlinks.modules.system.controller.dataAnalysis;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.modules.system.controller.dataAnalysis.vo.IndexTrendVO;
import com.sxlinks.modules.system.controller.dataAnalysis.vo.TotalTrendVO;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.DeviceProperty;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.service.productCenter.IDevicePropertyService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;
import com.sxlinks.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.*;

/**
 * <p>
 * 指标趋势表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dataAnalysis/indexTrend")
@Slf4j
@Api(value = "数据分析", tags = {"指标趋势"})
public class IndexTrendController {
	@Autowired
	private IProductService productService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private IDevicePropertyService devicePropertyService;

	/**
	 * 分页列表查询

	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取指标趋势数据列表", tags = "指标趋势")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONObject queryPageList(@RequestParam(name="productCode" ) String productCode,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="20") Integer pageSize,
									HttpServletRequest req) {
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		Result<IPage<IndexTrendVO>> result = new Result<IPage<IndexTrendVO>>(); //返回的是IndexTrendVO

		if(StringUtils.isNotNullOrEmpty(productCode)) {
			QueryWrapper<Device> queryDevice = new QueryWrapper<Device>().eq("product_code", productCode);
			//非管理员情况下，只能查看当前用户数据
			if (!user.getUsername().equals("admin")) queryDevice.eq("create_by", user.getId());
			Page<Device> page = new Page<Device>(pageNo, pageSize);
			IPage<Device> pageList = deviceService.page(page, queryDevice);

			List lsDevice = pageList.getRecords();
			List lsIndexTrend = new ArrayList();
			for (int i = 0; i < lsDevice.size(); i++) {
				Device p = (Device) lsDevice.get(i);
				IndexTrendVO vo = new IndexTrendVO();
				vo.setDeviceName(p.getDeviceName());
				vo.setDeviceCode(p.getDeviceCode());
				vo.setEnableStatus(p.getEnableStatus());
				vo.setActiveStatus(p.getActiveStatus());
				vo.setLastOnlineTime(p.getLastOnlineTime());

				int propertyCount = 0;    //属性总数

				QueryWrapper queryProperty = new QueryWrapper<DeviceProperty>().eq("device_code", p.getDeviceCode());
				//非管理员情况下，只能查看当前用户数据
				if (!user.getUsername().equals("admin")) queryProperty.eq("create_by", user.getId());
				propertyCount = devicePropertyService.count(queryProperty);
				vo.setPropertyCount(propertyCount);

				int propertyEnableCount = 0;    //属性激活总数
				QueryWrapper queryPropertyEnable = new QueryWrapper<DeviceProperty>().eq("status", "1").eq("device_code", p.getDeviceCode());
				//非管理员情况下，只能查看当前用户数据
				if (!user.getUsername().equals("admin")) queryPropertyEnable.eq("create_by", user.getId());
				propertyEnableCount = devicePropertyService.count(queryPropertyEnable);
				vo.setPropertyEnableCount(propertyEnableCount);

				lsIndexTrend.add(vo);
			}


		IPage<IndexTrendVO> pageList2=new Page<IndexTrendVO>();
		pageList2.setCurrent(pageList.getCurrent());
		pageList2.setPages(pageList.getPages());
		pageList2.setSize(pageList.getSize());
		pageList2.setTotal(pageList.getTotal());
		pageList2.setRecords(lsIndexTrend);

		result.setSuccess(true);
		result.setResult(pageList2);

		}

		JSONObject jo= JSON.parseObject(JSON.toJSONString(result));


		return jo;
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
			result.error500("未找到指标趋势信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验指标趋势编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证指标趋势编码是否唯一---id:"+id+"--roleCode:"+code);
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
		mv.addObject(NormalExcelConstants.FILE_NAME,"指标趋势列表");
		mv.addObject(NormalExcelConstants.CLASS,Product.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("指标趋势列表数据","导出人:"+user.getRealname(),"导出信息"));
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
