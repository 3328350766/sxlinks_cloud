package com.sxlinks.modules.system.controller.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.constant.CommonConstant;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.system.vo.LoginUser;
import com.sxlinks.modules.base.service.BaseCommonService;
import com.sxlinks.modules.system.controller.report.constant.ReportTypeEnum;
import com.sxlinks.modules.system.entity.report.*;
import com.sxlinks.modules.system.service.report.ReportExcelService;
import com.sxlinks.modules.system.service.report.ReportShareService;
import com.sxlinks.modules.system.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO
 * Report表 类型为report_excel（表格）的数据
 * @author chenkening
 * @date 2021/3/26 10:19
 */
@RestController
@Api(tags = "pc-表格报表设计管理")
@RequestMapping("/reportExcel")
public class ReportExcelController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportShareService reportShareService;

    @Autowired
    private ReportExcelService reportExcelService;

    @Resource
    private BaseCommonService baseCommonService;

    @ApiOperation(value = "查询")
    @GetMapping(value = "/query")
    public Result<IPage> query(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                               @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize,
                               ReportExcel report,
                               HttpServletRequest req) {
//        report.setReportType(ReportTypeEnum.report_excel.name());
        QueryWrapper<ReportExcel> queryWrapper = QueryGenerator.initQueryWrapper(report, req.getParameterMap());
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //非管理员情况下，只能查看当前用户数据
        if(!user.getUsername().equals("admin"))queryWrapper.eq("create_by",user.getId());
        IPage<ReportExcel> iPage = reportExcelService.page(new Page<>(pageNo,pageSize),queryWrapper);
        //日志记录
        baseCommonService.addLog("用户名: " + user.getUsername() + ",获取表格报表数据列表！", CommonConstant.LOG_TYPE_2, CommonConstant.OPERATE_TYPE_1,user);

        return Result.OK(iPage);
    }

    @ApiOperation(value = "明细")
    @GetMapping(value = "/{id}")
    public Result detail(@PathVariable Long id) {
        ReportExcel report = reportExcelService.getById(id);
        return Result.OK(report);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody ReportExcelDto reportDto) {
        ReportExcel report = new ReportExcel();
        BeanUtils.copyProperties(reportDto,report);
        //report.setReportType(ReportTypeEnum.report_excel.name());
        reportExcelService.save(report);
        return Result.OK();
    }

    @ApiOperation(value = "更新")
    @PutMapping(value = "/update")
    public Result update(@RequestBody ReportExcelDto reportDto) {
        ReportExcel report = new ReportExcel();
        report=reportExcelService.getById(reportDto.getId());
        BeanUtils.copyProperties(reportDto,report);
        //report.setReportType(ReportTypeEnum.report_excel.name());
        reportExcelService.updateById(report);
        return Result.OK();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Long id) {
        reportExcelService.removeById(id);
        return Result.OK();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/delete/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        reportExcelService.removeByIds(ids);
        return Result.OK();
    }

    @ApiOperation(value = "复制")
    @PostMapping("/copy")
    public Result copy(@RequestBody ReportExcelDto dto) {
        reportExcelService.copy(dto);
        return null;
//        return ResponseBean.builder().build();
    }

    @PostMapping("/share")
    @ApiOperation(value = "excel分享")
    public Result share(@Validated @RequestBody ReportShareDto dto) {
        ReportShareDto reportShareDto = reportShareService.insertShare(dto);
        return Result.OK(reportShareDto);
    }

    @GetMapping("/detailByReportCode/{reportCode}")
    @ApiOperation(value = "详情")
    public Result detailByReportCode(@PathVariable String reportCode) {
        ReportExcelDto reportExcelDto = reportExcelService.detailByReportCode(reportCode);
        return Result.OK(reportExcelDto);
    }

    @PostMapping("/preview")
    @ApiOperation(value = "预览")
    public Result preview(@RequestBody ReportExcelDto reportExcelDto) {
        ReportExcelDto result = reportExcelService.preview(reportExcelDto);
        return Result.OK(result);
    }


    @PostMapping("/exportExcel")
    @ApiOperation(value = "报表导出")
    public Result exportExcel(@RequestBody ReportExcelDto reportExcelDto) {
        reportExcelService.exportExcel(reportExcelDto);
        return Result.OK("导出成功，请稍后在文件管理中查看");
    }
}
