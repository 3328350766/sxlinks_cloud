package com.sxlinks.modules.system.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.modules.system.controller.report.constant.ReportTypeEnum;
import com.sxlinks.modules.system.entity.report.Report;
import com.sxlinks.modules.system.entity.report.ReportDto;
import com.sxlinks.modules.system.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 * Report表 类型为report_screen（大屏）的数据
 * @author chenkening
 * @date 2021/3/26 10:19
 */
@RestController
@Api(tags = "pc-大屏")
@RequestMapping("/reportScreen")
public class ReportScreenController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "查询")
    @GetMapping(value = "/query")
    public Result<IPage> query(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                               @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize,
                               Report report,
                               HttpServletRequest req) {
        report.setReportType(ReportTypeEnum.report_screen.name());
        QueryWrapper<Report> queryWrapper = QueryGenerator.initQueryWrapper(report, req.getParameterMap());
        IPage<Report> iPage = reportService.page(new Page<>(pageNo,pageSize),queryWrapper);
        return Result.OK(iPage);
    }

    @ApiOperation(value = "明细")
    @GetMapping(value = "/{id}")
    public Result detail(@PathVariable Long id) {
        Report report = reportService.getById(id);
        return Result.OK(report);
    }

//    @ApiOperation(value = "code查询")
//    @GetMapping(value = "/{code|")
//    public Result getByCode(@PathVariable String reportCode) {
//        Report report = reportService.getByCode(reportCode);
//        return Result.OK(report);
//    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody ReportDto reportDto) {
        Report report = new Report();
        BeanUtils.copyProperties(reportDto,report);
        report.setReportType(ReportTypeEnum.report_screen.name());
        reportService.save(report);
        return Result.OK();
    }

    @ApiOperation(value = "更新")
    @PutMapping(value = "/update")
    public Result update(@RequestBody ReportDto reportDto) {
        Report report = new Report();
        BeanUtils.copyProperties(reportDto,report);
        report.setReportType(ReportTypeEnum.report_screen.name());
        reportService.updateById(report);
        return Result.OK();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Long id) {
        reportService.removeById(id);
        return Result.OK();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/delete/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        reportService.removeByIds(ids);
        return Result.OK();
    }

    @ApiOperation(value = "复制")
    @PostMapping("/copy")
    public Result copy(@RequestBody ReportDto dto) {
        if (Objects.isNull(dto.getId())) {
            return Result.error("报表id不能为空");
        }
        if (StringUtils.isBlank(dto.getReportCode())) {
            return Result.error("报表编码不能为空");
        }
        reportService.copy(dto);
        return Result.OK();
    }
}
