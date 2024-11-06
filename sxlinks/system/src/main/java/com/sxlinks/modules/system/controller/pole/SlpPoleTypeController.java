package com.sxlinks.modules.system.controller.pole;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.pole.SlpPoleType;
import com.sxlinks.modules.system.service.pole.ISlpPoleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 灯杆类型Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "pc-灯柱类型管理")
@RestController
@RequestMapping("/poleType")
public class SlpPoleTypeController {

    @Autowired
    private ISlpPoleTypeService slpPoleTypeService;

    /**
     * 查询灯杆类型列表
     */
    @ApiOperation(value = "查询灯杆类型列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       SlpPoleType slpPoleType,
                       HttpServletRequest req) {
        if (StringUtils.isNotBlank(slpPoleType.getPoleTypeName())) {
            slpPoleType.setPoleTypeName(StringUtils.join("*",slpPoleType.getPoleTypeName(),"*"));
        }
        Result<IPage<SlpPoleType>> result = new Result<>();
        QueryWrapper<SlpPoleType> queryWrapper = QueryGenerator.initQueryWrapper(slpPoleType, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
        Page<SlpPoleType> page = new Page<>(pageNo, pageSize);
        IPage<SlpPoleType> pageList = slpPoleTypeService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 导出灯杆类型列表
     */
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SlpPoleType slpPoleType) {
//        List<SlpPoleType> list = slpPoleTypeService.selectSlpPoleTypeList(slpPoleType);
//        ExcelUtil<SlpPoleType> util = new ExcelUtil<>(SlpPoleType. class);
//        util.exportExcel(response, list, "灯杆类型数据");
//    }

    /**
     * 获取灯杆类型详细信息
     */
    @ApiOperation(value = "获取灯杆类型详细信息")
    @GetMapping(value = "/query/{poleTypeId}")
    public Result getInfo(@PathVariable("poleTypeId") Long poleTypeId) {
        slpPoleTypeService.selectSlpPoleTypeByPoleTypeId(poleTypeId);
        return Result.OK();
    }

    /**
     * 新增灯杆类型
     */
    @ApiOperation(value = "新增灯杆类型")
    @PostMapping(value = "/add")
    public Result add(@RequestBody SlpPoleType slpPoleType) {
        slpPoleTypeService.insertSlpPoleType(slpPoleType);
        return Result.OK();
    }

    /**
     * 修改灯杆类型
     */
    @ApiOperation(value = "修改灯杆类型")
    @PutMapping(value = "/edit")
    public Result edit(@RequestBody SlpPoleType slpPoleType) {
        slpPoleTypeService.updateSlpPoleType(slpPoleType);
        return Result.OK();
    }

    /**
     * 删除灯杆类型
     */
    @ApiOperation(value = "删除灯杆类型")
    @DeleteMapping("/remove/{poleTypeIds}")
    public Result remove(@PathVariable Long[] poleTypeIds) {
        slpPoleTypeService.deleteSlpPoleTypeByPoleTypeIds(poleTypeIds);
        return Result.OK();
    }
}
