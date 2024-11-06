package com.sxlinks.modules.system.controller.pole;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.entity.pole.SlpPole;
import com.sxlinks.modules.system.entity.pole.SlpPoleType;
import com.sxlinks.modules.system.entity.productCenter.Project;
import com.sxlinks.modules.system.service.pole.ISlpPoleService;
import com.sxlinks.modules.system.service.pole.ISlpPoleTypeService;
import com.sxlinks.modules.system.service.productCenter.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 灯杆信息Controller
 *
 * @author wll
 * @date 2022-12-13
 */
@Api(tags = "pc-灯柱管理")
@RestController
@RequestMapping("/pole")
public class SlpPoleController {

    @Autowired
    private ISlpPoleService slpPoleService;

    @Autowired
    private IProjectService iProjectService;

    @Autowired
    private ISlpPoleTypeService iSlpPoleTypeService;

    /**
     * 查询灯杆信息列表
     */
    @ApiOperation(value = "查询灯杆信息列表")
    @GetMapping("/list")
    public Result list(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                       SlpPole slpPole,
                       HttpServletRequest req) {
        if (StringUtils.isNotBlank(slpPole.getPoleName())) {
            slpPole.setPoleName(StringUtils.join("*",slpPole.getPoleName(),"*"));
        }
        Result<IPage<SlpPole>> result = new Result<>();
        QueryWrapper<SlpPole> queryWrapper = QueryGenerator.initQueryWrapper(slpPole, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
        Page<SlpPole> page = new Page<>(pageNo, pageSize);
        IPage<SlpPole> pageList = slpPoleService.page(page, queryWrapper);
        trans(pageList.getRecords());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 导出灯杆信息列表
     */
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SlpPole slpPole) {
//        List<SlpPole> list = slpPoleService.selectSlpPoleList(slpPole);
//        ExcelUtil<SlpPole> util = new ExcelUtil<>(SlpPole. class);
//        util.exportExcel(response, list, "灯杆信息数据");
//    }

    /**
     * 获取灯杆信息详细信息
     */
    @ApiOperation(value = "获取灯杆信息详细信息")
    @GetMapping(value = "/query/{poleId}")
    public Result getInfo(@PathVariable("poleId") Long poleId) {
        SlpPole slpPole = slpPoleService.selectSlpPoleByPoleId(poleId);
        return Result.OK(slpPole);
    }

    /**
     * 新增灯杆信息
     */
    @ApiOperation(value = "新增灯杆信息")
    @PostMapping("/add")
    public Result add(@RequestBody SlpPole slpPole) {
        slpPoleService.insertSlpPole(slpPole);
        return Result.OK();
    }

    /**
     * 修改灯杆信息
     */
    @ApiOperation(value = "修改灯杆信息")
    @PutMapping("/edit")
    public Result edit(@RequestBody SlpPole slpPole) {
        slpPoleService.updateSlpPole(slpPole);
        return Result.OK();
    }

    /**
     * 删除灯杆信息
     */
    @ApiOperation(value = "删除灯杆信息")
    @DeleteMapping("/remove/{poleIds}")
    public Result remove(@PathVariable Long[] poleIds) {
        slpPoleService.deleteSlpPoleByPoleIds(poleIds);
        return Result.OK();
    }

    private void trans(List<SlpPole> list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            List<Long> ids = list.stream().map(SlpPole::getPoleProjectId).distinct().collect(Collectors.toList());
            List<Project> projectList = iProjectService.listByIds(ids);
            Map<Long,String> idNameMap = projectList.stream().collect(Collectors.toMap(Project::getId,Project::getName));
            for (SlpPole pole : list) {
                pole.setProjectName(idNameMap.get(pole.getPoleProjectId()));
            }

            List<String> typeIds = list.stream().filter(slpPole -> StringUtils.isNotEmpty(slpPole.getPoleTypeId())).map(SlpPole::getPoleTypeId).distinct().collect(Collectors.toList());
            List<SlpPoleType> poleTypeList = iSlpPoleTypeService.listByIds(typeIds);
            Map<String,String> idTypeNameMap = poleTypeList.stream().collect(Collectors.toMap(SlpPoleType::getPoleTypeId,SlpPoleType::getPoleTypeName));
            for (SlpPole pole : list) {
                pole.setPoleTypeName(idTypeNameMap.get(pole.getPoleTypeId()));
            }
        }

        for (SlpPole pole : list) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(pole.getPoleEnable())){
                switch (pole.getPoleEnable()){
                    case "0":
                        pole.setPoleEnable("启用");
                    case "1":
                        pole.setPoleEnable("停用");
                }
            }
        }
    }
}
