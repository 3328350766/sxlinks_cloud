package com.sxlinks.modules.api.controller.miniProgram.visual;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxlinks.common.api.vo.Result;
import com.sxlinks.common.system.query.QueryGenerator;
import com.sxlinks.common.util.oConvertUtils;
import com.sxlinks.modules.system.entity.report.*;
import com.sxlinks.modules.system.service.report.ReportDashboardService;
import com.sxlinks.modules.system.service.report.ReportDashboardWidgetService;
import com.sxlinks.modules.system.service.report.ReportExcelService;
import com.sxlinks.modules.system.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 *
 * @author chenkening
 * @date 2021/3/26 10:19
 */
@RestController
@Api(tags = "pc-报表管理",hidden = true)
@RequestMapping("/miniProgram/report")
public class MiniReportManageController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportDashboardService reportDashboardService;
    @Autowired
    private ReportDashboardWidgetService reportDashboardWidgetService;
    @Autowired
    private ReportExcelService reportExcelService;

    @ApiOperation(value = "查询")
    @GetMapping(value = "/query")
    public Result<IPage> query(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                               @RequestParam(name="pageSize", defaultValue = "10") Integer pageSize,
                               Report report,
                               HttpServletRequest req) {
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

    @ApiOperation(value = "新增")
    @PostMapping(value = "/insert")
    public Result insert(@RequestBody ReportDto reportDto) {
        Report report = new Report();
        BeanUtils.copyProperties(reportDto,report);
        Result rst=checkCode(null,reportDto.getReportCode());
        if(rst.isSuccess()) { //进行编码验证
            reportService.save(report);
        }else{
            return rst;
        }
        return Result.OK();
    }

    @ApiOperation(value = "更新")
    @PutMapping(value = "/update")
    public Result update(@RequestBody ReportDto reportDto) {
        Report report = new Report();
        BeanUtils.copyProperties(reportDto,report);
        reportService.updateById(report);
        return Result.OK();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) Long id) {
        Report report=reportService.getById(id);
        if(report.getReportType().equals("report_screen")){ //删除大屏下面的组件
            //删除reportDashboardWidget
            reportDashboardService.remove(new QueryWrapper<ReportDashboard>()
                    .lambda().eq(ReportDashboard::getReportCode, report.getReportCode()));
            //删除reportDashboardWidget
            reportDashboardWidgetService.remove(new QueryWrapper<ReportDashboardWidget>()
                    .lambda().eq(ReportDashboardWidget::getReportCode, report.getReportCode()));
        }
        if(report.getReportType().equals("report_excel")){ //删除excel报表下面的组件
            //删除excel组件
            reportExcelService.remove(new QueryWrapper<ReportExcel>()
                    .lambda().eq(ReportExcel::getReportCode, report.getReportCode()));
        }
        reportService.removeById(id);
        return Result.OK();
    }

//    @ApiOperation(value = "批量删除")
//    @PostMapping(value = "/delete/batch")
//    public Result deleteBatch(@RequestBody List<Long> ids) {
//        reportService.removeByIds(ids);
//        return Result.OK();
//    }

    @ApiOperation(value = "复制")
    @PostMapping("/copy")
    public Result copy(@RequestBody ReportDto dto) {
        reportService.copy(dto);
        return null;
//        return ResponseBean.builder().build();
    }

    /**
     * 校验规则编码唯一
     */
    @ApiOperation(value = "校验规则编码唯一")
    @RequestMapping(value = "/checkCode", method = RequestMethod.GET)
    public Result<Boolean> checkCode(String id,String code) {
        Result<Boolean> result = new Result<>();
        result.setResult(true);//如果此参数为false则程序发生异常
        //log.info("--验证规则编码是否唯一---id:"+id+"--roleCode:"+code);
        try {
            Report role = null;
            if(oConvertUtils.isNotEmpty(id)) {
                role = reportService.getById(id);
            }
            Report newRole = reportService.getOne(new QueryWrapper<Report>().lambda().eq(Report::getReportCode, code));
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

}
