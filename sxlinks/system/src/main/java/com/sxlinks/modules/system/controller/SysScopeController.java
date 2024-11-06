package com.sxlinks.modules.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.SysScope;
import com.sxlinks.modules.system.model.SysScopeSimple;
import com.sxlinks.modules.system.service.ISysScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 区域 前端控制器
 * </p>
 *
 * @Author sxlinks
 * @since 2022-06-02
 */
@Api(tags = "pc-区域服务")
@RestController
@RequestMapping("/sys/area")
@Slf4j
public class SysScopeController {

	@Autowired
	private ISysScopeService iSysScopeService;

	@ApiOperation(value = "区域分页")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<SysScope>> queryPageList(SysScope area, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
		Result<IPage<SysScope>> result = new Result<IPage<SysScope>>();
		QueryWrapper<SysScope> queryWrapper = QueryGenerator.initQueryWrapper(area, req.getParameterMap());
		Page<SysScope> page = new Page<SysScope>(pageNo, pageSize);
		IPage<SysScope> pageList = iSysScopeService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ApiOperation(value = "查询子区域")
	@RequestMapping(value = "/queryByParent", method = RequestMethod.GET)
	public Result<List<SysScope>> queryByParent(@ApiParam(value = "默认传0:所有省级数据,否则传areaCode值") @RequestParam(name="parentCode", defaultValue="0") String parentCode) {
		Result<List<SysScope>> result = new Result<List<SysScope>>();
		List<SysScope> list = iSysScopeService.getByParent(parentCode);
		result.setSuccess(true);
		result.setResult(list);
		return result;
	}

	@ApiOperation(value = "查询省市区")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public Result<List<SysScopeSimple>> tree() {
		List<SysScopeSimple> list = iSysScopeService.findAll();

		Map<Integer,List<SysScopeSimple>> scopeMap = list.stream().collect(Collectors.groupingBy(SysScopeSimple::getLevel));

		List<SysScopeSimple> list0 = scopeMap.get(0);

		List<SysScopeSimple> list1 = scopeMap.get(1);
		Map<String,List<SysScopeSimple>> scopeMap1 = list1.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list2 = scopeMap.get(2);
		Map<String,List<SysScopeSimple>> scopeMap2 = list2.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		list0.forEach(simple1 -> {
			List<SysScopeSimple> sonList1 = scopeMap1.get(simple1.getValue());
			simple1.setChildren(sonList1);

			if (Objects.nonNull(sonList1)){
				sonList1.forEach(simple2 -> {
					List<SysScopeSimple> sonList2 = scopeMap2.get(simple2.getValue());
					simple2.setChildren(sonList2);
				});
			}
		});

		return Result.OK(list0);
	}

	@ApiOperation(value = "查询子区域4",hidden = true)
	@RequestMapping(value = "/tree4", method = RequestMethod.GET)
	public Result<List<SysScopeSimple>> tree4() {
		List<SysScopeSimple> list = iSysScopeService.findAll();

		Map<Integer,List<SysScopeSimple>> scopeMap = list.stream().collect(Collectors.groupingBy(SysScopeSimple::getLevel));

//		List<SysScopeSimple> list0 = scopeMap.get(0);

		List<SysScopeSimple> list1 = scopeMap.get(1);
		Map<String,List<SysScopeSimple>> scopeMap1 = list1.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list2 = scopeMap.get(2);
		Map<String,List<SysScopeSimple>> scopeMap2 = list2.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list3 = scopeMap.get(3);
		Map<String,List<SysScopeSimple>> scopeMap3 = list3.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list4 = scopeMap.get(4);
		Map<String,List<SysScopeSimple>> scopeMap4 = list4.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));
		list1.forEach(simple1 -> {
			List<SysScopeSimple> sonList1 = scopeMap2.get(simple1.getValue());
			simple1.setChildren(sonList1);

			if (Objects.nonNull(sonList1)){
				sonList1.forEach(simple2 -> {
					List<SysScopeSimple> sonList2 = scopeMap3.get(simple2.getValue());
					simple2.setChildren(sonList2);

					if (Objects.nonNull(sonList2)){
						sonList2.forEach(simple3 -> {
							List<SysScopeSimple> sonList3 = scopeMap4.get(simple3.getValue());
							simple3.setChildren(sonList3);
						});
					}
				});
			}
		});

		return Result.OK(list1);
	}

	@ApiOperation(value = "查询子区域2",hidden = true)
	@RequestMapping(value = "/tree2", method = RequestMethod.GET)
	public Result<List<SysScopeSimple>> tree2() {
		List<SysScopeSimple> list = iSysScopeService.findAll();

		Map<Integer,List<SysScopeSimple>> scopeMap = list.stream().collect(Collectors.groupingBy(SysScopeSimple::getLevel));

//		List<SysScopeSimple> list0 = scopeMap.get(0);

		List<SysScopeSimple> list1 = scopeMap.get(1);
		Map<String,List<SysScopeSimple>> scopeMap1 = list1.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list2 = scopeMap.get(2);
		Map<String,List<SysScopeSimple>> scopeMap2 = list2.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list3 = scopeMap.get(3);
		Map<String,List<SysScopeSimple>> scopeMap3 = list3.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list4 = scopeMap.get(4);
		Map<String,List<SysScopeSimple>> scopeMap4 = list4.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));
		list1.forEach(simple1 -> {
			List<SysScopeSimple> sonList1 = scopeMap2.get(simple1.getValue());
			simple1.setChildren(sonList1);

//			if (Objects.nonNull(sonList1)){
//				sonList1.forEach(simple2 -> {
//					List<SysScopeSimple> sonList2 = scopeMap3.get(simple2.getValue());
//					simple2.setChildren(sonList2);
//
//					if (Objects.nonNull(sonList2)){
//						sonList2.forEach(simple3 -> {
//							List<SysScopeSimple> sonList3 = scopeMap4.get(simple3.getValue());
//							simple3.setChildren(sonList3);
//						});
//					}
//				});
//			}
		});

		return Result.OK(list1);
	}

	@ApiOperation(value = "查询子区域3",hidden = true)
	@RequestMapping(value = "/tree3", method = RequestMethod.GET)
	public Result<List<SysScopeSimple>> tree3() {
		List<SysScopeSimple> list = iSysScopeService.findAll();

		Map<Integer,List<SysScopeSimple>> scopeMap = list.stream().collect(Collectors.groupingBy(SysScopeSimple::getLevel));

		List<SysScopeSimple> list0 = scopeMap.get(0);

		List<SysScopeSimple> list1 = scopeMap.get(1);
		Map<String,List<SysScopeSimple>> scopeMap1 = list1.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		List<SysScopeSimple> list2 = scopeMap.get(2);
		Map<String,List<SysScopeSimple>> scopeMap2 = list2.stream().collect(Collectors.groupingBy(SysScopeSimple::getParentCode));

		list0.forEach(simple1 -> {
			List<SysScopeSimple> sonList1 = scopeMap1.get(simple1.getValue());
			simple1.setChildren(sonList1);

			if (Objects.nonNull(sonList1)){
				sonList1.forEach(simple2 -> {
					List<SysScopeSimple> sonList2 = scopeMap2.get(simple2.getValue());
					simple2.setChildren(sonList2);
				});
			}
		});

		return Result.OK(list0);
	}
}
