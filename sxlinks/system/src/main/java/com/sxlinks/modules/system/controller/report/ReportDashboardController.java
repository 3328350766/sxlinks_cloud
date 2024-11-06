package com.sxlinks.modules.system.controller.report;

import com.sxlinks.common.api.vo.Result;
import com.sxlinks.modules.system.entity.report.ChartDto;
import com.sxlinks.modules.system.entity.report.ReportDashboardObjectDto;
import com.sxlinks.modules.system.service.report.ReportShareService;
import com.sxlinks.modules.system.service.report.ReportDashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
* @desc 大屏设计 controller
* @website https://gitee.com/anji-plus/gaea
* @author Raod
* @date 2021-04-12 14:52:21.761
**/
@RestController
@Api(tags = "pc-大屏设计管理")
@RequestMapping("/reportDashboard")
public class ReportDashboardController {

    @Autowired
    private ReportDashboardService reportDashboardService;

    @Autowired
    private ReportShareService reportShareService;

    /**
     * 预览、查询大屏详情
     * @param reportCode
     * @return
     */
    @ApiOperation(value = "查看大屏")
    @GetMapping({"/{reportCode}"})
    public Result detail(@PathVariable("reportCode") String reportCode) {
        ReportDashboardObjectDto objectDto = reportDashboardService.getDetail(reportCode);
        return Result.OK(objectDto);
    }

    /**
     * 保存大屏设计
     * @param dto
     * @return
     */
    @PostMapping(value = "/insert")
    @ApiOperation(value = "保存大屏设计")
    public Result insert(@RequestBody ReportDashboardObjectDto dto) {
        if (Objects.isNull(dto.getReportCode())) {
            return Result.error("code不能为空");
        }
        reportDashboardService.insertDashboard(dto);
        return Result.OK();
    }


    /**
     * 获取去单个图层数据
     * @param dto
     * @return
     */
    @ApiOperation(value = "获取去单个图层数据")
    @PostMapping("/getData")
    public Result getData(@RequestBody ChartDto dto) {
        Object o = reportDashboardService.getChartData(dto);
        return Result.OK(o);
    }


//    /**
//     * 导出大屏
//     * @param reportCode
//     * @return
//     */
//    @ApiOperation(value = "导出大屏")
//    @GetMapping("/export")
//    public ResponseEntity<byte[]> exportDashboard(HttpServletRequest request, HttpServletResponse response,
//                                                  @RequestParam("reportCode") String reportCode, @RequestParam(value = "showDataSet",required = false, defaultValue = "1") Integer showDataSet) {
//        return reportDashboardService.exportDashboard(request, response, reportCode, showDataSet);
//    }
//
//    /**
//     * 导入大屏
//     * @param file  导入的zip文件
//     * @param reportCode
//     * @return
//     */
//    @ApiOperation(value = "导入大屏")
//    @PostMapping("/import/{reportCode}")
//    public ResponseBean importDashboard(@RequestParam("file") MultipartFile file, @PathVariable("reportCode") String reportCode) {
//        reportDashboardService.importDashboard(file, reportCode);
//        return ResponseBean.builder().build();
//    }
//
//    @ApiOperation(value = "分享")
//    @PostMapping("/share")
//    public ResponseBean share(@Validated @RequestBody ReportShareDto dto) {
//        return ResponseBean.builder().data(reportShareService.insertShare(dto)).build();
//    }

}
