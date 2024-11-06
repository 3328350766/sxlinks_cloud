package com.sxlinks.modules.system.controller.dataAnalysis;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.domain.DataResult;
import com.sxlinks.common.domain.dto.PageReqDto;
import com.sxlinks.common.domain.dto.PageResDto;
import com.sxlinks.common.domain.dto.request.device.DeviceRtItemReqDto;
import com.sxlinks.common.domain.dto.response.device.DeviceRtHistoryResDto;
import com.sxlinks.dao.cache.impl.RedisDaoImpl;
import com.sxlinks.modules.system.entity.productCenter.Device;
import com.sxlinks.modules.system.entity.productCenter.Product;
import com.sxlinks.modules.system.service.dataCenter.IDeviceDataService;
import com.sxlinks.modules.system.service.productCenter.IDeviceService;

import com.sxlinks.modules.system.vo.AggregateConditionVO;
import com.sxlinks.modules.system.vo.IndexAggregateVO;
import com.sxlinks.plugin.es.domain.SearchItem;
import com.sxlinks.storage.IDataCenterService;
import com.sxlinks.utils.DateUtil;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 指标聚合表 前端控制器
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/dataAnalysis/indexAggregate")
@Slf4j
@Api(value = "数据分析", tags = {"指标聚合"})
public class IndexAggregateController {
	@Autowired
	private IProductService productService;

	@Autowired
	private IDeviceDataService deviceDataService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private RedisDaoImpl redisDao; //redis缓存
	@Resource
	private IDataCenterService dataCenterService;   //es索引操作类

	/**
	 * 分页列表查询
	 * @param data
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value = "获取指标聚合数据列表", tags = "指标聚合")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Result<JSONObject> queryPageList(@RequestBody IndexAggregateVO data,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		Result<JSONObject> result = new Result<JSONObject>();
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		String strDate= DateUtil.getDate();	//获取当前日期
		if(StringUtils.isNotNullOrEmpty(data)&&StringUtils.isNotNullOrEmpty(data.getStartDate())){
			strDate=data.getStartDate();
		}
		String startTime=strDate+" 00:00:01";
		String endTime=strDate+"  23:59:59";

		JSONObject joResult=new JSONObject();
		if(StringUtils.isNotNullOrEmpty(data)&&StringUtils.isNotNullOrEmpty(data.getCondition())){
			List lsCondition=data.getCondition();
			for(int i=0;i<lsCondition.size();i++){
				AggregateConditionVO acv=(AggregateConditionVO)lsCondition.get(i);
				Device device=deviceService.getOne(new QueryWrapper<Device>().eq("device_code",acv.getDeviceCode()));
				HashMap lsValue=processData(acv,startTime,endTime);
//				JSONObject joItem=new JSONObject();
//
				//joResult.put(acv.getPropertyCode(),lsValue); //输出的是产品属性和值
				joResult.put(device.getDeviceName(),lsValue); //输出的是产品属性和值
			}
		}

		result.setSuccess(true);
		result.setResult(joResult);
		return result;
	}

	public List<DeviceRtHistoryResDto> getData(String startTime,String endTime,String productCode,String deviceCode,String propertyCode){
		List<DeviceRtHistoryResDto> lsData=new ArrayList();
		PageReqDto<DeviceRtItemReqDto> searchPage1=new PageReqDto();
		searchPage1.setPageNo(1);
		searchPage1.setLimit(10000); //最大取2万条
		DeviceRtItemReqDto dto1=new DeviceRtItemReqDto();
		dto1.setStartDate(DateUtil.parseTime(startTime));
		dto1.setEndDate(DateUtil.parseTime(endTime));
		dto1.setFuncType("PROP");
		dto1.setProductCode(productCode);
		dto1.setDeviceCode(deviceCode);
		dto1.setIdentifier(propertyCode);
		searchPage1.setParamData(dto1);
//		List lsSearchItem=new ArrayList();
//		SearchItem item=new SearchItem();
//		item.setKey("");//传字段值
//		item.setValue("");//传属性名
//		lsSearchItem.add(item);
		PageResDto<DeviceRtHistoryResDto> pageResult = deviceDataService.searchReportItem(searchPage1);
		lsData=pageResult.getResultData();
		return lsData;
	}

	public HashMap processData(AggregateConditionVO acv,String startTime,String endTime){
		List lsData=new ArrayList();
		HashMap map=new HashMap();
		if(StringUtils.isNotNullOrEmpty(acv.getSplitType())&&acv.getSplitType().equals("hour")){ //时段数据-hour 日段数据-day
			List lsData1=getData(startTime,endTime,acv.getProductCode(),acv.getDeviceCode(),acv.getPropertyCode());
			for(int i=0;i<24;i++){ //提取小时数据
				int hour=i;
				List lsHour=filterHourData(lsData1,hour);
				if(StringUtils.isNotNullOrEmpty(acv.getType())&&acv.getType().equals("avg")) { //平均值-avg 累计值-total
					String value=computeAvg(lsHour);
					//lsData.add(value);
					map.put(hour,value);
				}
				if(StringUtils.isNotNullOrEmpty(acv.getType())&&acv.getType().equals("total")) { //平均值-avg 累计值-total
					String value=computeTotal(lsHour);
					//lsData.add(value);
					map.put(hour,value);
				}

			}
			//DateUtil.getCurrentHour()
		}
		return map;
	}
	public List filterHourData(List lsData,int hour){
		List ls=new ArrayList();
		for(int i=0;i<lsData.size();i++){
			DeviceRtHistoryResDto entity=(DeviceRtHistoryResDto)lsData.get(i);
			String strHour=DateUtil.getHour(entity.getTimestamp());
			if(Integer.parseInt(strHour)==hour){
				ls.add(entity);
			}
		}
		return ls;
	}
	public String computeAvg(List lsData){
		String rstr="0";
		Double d=0d;
		for(int i=0;i<lsData.size();i++){
			DeviceRtHistoryResDto entity=(DeviceRtHistoryResDto)lsData.get(i);
			d=d+Double.valueOf((String)entity.getRequest());
		}
		Double v=d/lsData.size();
		rstr=processValue(v);
		return rstr;
	}
	public String computeTotal(List lsData){
		//数组的最后一个值，减掉1第一个值,本次压入是es倒着返回数据
		//为了防止有断点数据，应该是取最大值和最小值
		String rstr="0";
		Double d=0d; //最小值
		Double d1=0d; //最大值
		if(lsData!=null &&lsData.size()>0) {

			for(int i=0;i<lsData.size();i++){

					DeviceRtHistoryResDto entity = (DeviceRtHistoryResDto) lsData.get(i);
					//Double di=Double.valueOf((String) entity.getRequest());
					if(Double.valueOf((String) entity.getRequest())>0d) {
						if (i == 0) { //第1个给最小，最大，都赋值
							d = Double.valueOf((String) entity.getRequest());
							d1 = Double.valueOf((String) entity.getRequest());
						} else {
							if(d==0d)d = Double.valueOf((String) entity.getRequest()); //遇到第1个数据为0时
							if(d1==0d)d1 = Double.valueOf((String) entity.getRequest());
							if (d > Double.valueOf((String) entity.getRequest())) {
								d = Double.valueOf((String) entity.getRequest());
							}
							if (d1 < Double.valueOf((String) entity.getRequest())) {
								d1 = Double.valueOf((String) entity.getRequest());
							}
						}
					}
			}
		}
		Double v=d1-d;
		rstr=processValue(v);
		return rstr;
	}
	//处理double的最后三位小数点
	public String processValue(Double d) {
		String rstr="";
		if(d!=0d) {
			DecimalFormat df=new DecimalFormat("#.###");
			rstr=df.format(d);
		}else {
			rstr="0";
		}
		return rstr;
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
			result.error500("未选中指标聚合！");
		}else {
			productService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除指标聚合成功!");
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
			result.error500("未找到指标聚合信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 校验指标聚合编码唯一
	 */
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public Result<Boolean> checkUsername(String id,String code) {
		Result<Boolean> result = new Result<>();
		result.setResult(true);//如果此参数为false则程序发生异常
		log.info("--验证指标聚合编码是否唯一---id:"+id+"--roleCode:"+code);
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
		mv.addObject(NormalExcelConstants.FILE_NAME,"指标聚合列表");
		mv.addObject(NormalExcelConstants.CLASS,Product.class);
		LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject(NormalExcelConstants.PARAMS,new ExportParams("指标聚合列表数据","导出人:"+user.getRealname(),"导出信息"));
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
