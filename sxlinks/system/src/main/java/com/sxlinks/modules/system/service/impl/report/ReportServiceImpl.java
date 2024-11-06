package com.sxlinks.modules.system.service.impl.report;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.enums.ReportTypeEnum;
import com.sxlinks.modules.system.entity.report.*;
import com.sxlinks.modules.system.mapper.report.ReportMapper;
import com.sxlinks.modules.system.service.report.ReportDashboardWidgetService;
import com.sxlinks.modules.system.service.report.ReportDashboardService;
import com.sxlinks.modules.system.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author chenkening
 * @date 2021/3/26 10:35
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Resource
    private ReportMapper reportMapper;

    @Autowired
    private ReportDashboardService reportDashboardService;
    @Autowired
    private ReportDashboardWidgetService reportDashboardWidgetService;
//    @Autowired
//    private ReportExcelService reportExcelService;

//    @Override
//    public GaeaBaseMapper<Report> getMapper() {
//        return reportMapper;
//    }


//    @Override
//    public void processBatchBeforeOperation(List<Report> entities, BaseOperationEnum operationEnum) throws BusinessException {
//        ReportService.super.processBatchAfterOperation(entities, operationEnum);
//        switch (operationEnum) {
//            case DELETE_BATCH:
//                entities.forEach(report -> {
//                    Long id = report.getId();
//                    Report delReport = selectOne(id);
//                    if (null == delReport) {
//                        return;
//                    }
//                    String reportCode = delReport.getReportCode();
//                    String reportType = delReport.getReportType();
//                    switch (ReportTypeEnum.valueOf(reportType)) {
//                        case report_screen:
//                            LambdaQueryWrapper<ReportDashboard> reportDashboardLambdaQueryWrapper = Wrappers.lambdaQuery();
//                            reportDashboardLambdaQueryWrapper.eq(ReportDashboard::getReportCode, reportCode);
//                            reportDashboardService.delete(reportDashboardLambdaQueryWrapper);
//
//                            LambdaQueryWrapper<ReportDashboardWidget> reportDashboardWidgetLambdaQueryWrapper = Wrappers.lambdaQuery();
//                            reportDashboardWidgetLambdaQueryWrapper.eq(ReportDashboardWidget::getReportCode, reportCode);
//                            reportDashboardWidgetService.delete(reportDashboardWidgetLambdaQueryWrapper);
//
//                            break;
//                        case report_excel:
//                            LambdaQueryWrapper<ReportExcel> reportExcelLambdaQueryWrapper = Wrappers.lambdaQuery();
//                            reportExcelLambdaQueryWrapper.eq(ReportExcel::getReportCode, reportCode);
//                            reportExcelService.delete(reportExcelLambdaQueryWrapper);
//                            break;
//                        default:
//                    }
//                });
//                break;
//            default:
//
//        }
//    }

    /**
     * 下载次数+1
     *
     * @param reportCode
     */
    @Override
    public void downloadStatistics(String reportCode) {
//        Report report = selectOne("report_code", reportCode);
//        if (null != report) {
//            Long downloadCount = report.getDownloadCount();
//            if (null == downloadCount) {
//                downloadCount = 0L;
//            }else {
//                downloadCount++;
//            }
//            report.setDownloadCount(downloadCount);
//            update(report);
//        }

    }

    @Override
    public void copy(ReportDto dto) {
        Report report = getById(dto.getId());
        String reportCode = report.getReportCode();
        Report copyReport = copyReport(report, dto);
        //复制主表数据
        save(copyReport);
        String copyReportCode = copyReport.getReportCode();
        String reportType = report.getReportType();
        switch (ReportTypeEnum.valueOf(reportType)) {
            case report_screen:
                //查询看板
                QueryWrapper<ReportDashboard> wrapper = new QueryWrapper();
                wrapper.eq("report_code",reportCode);
                ReportDashboard reportDashboard = reportDashboardService.getOne(wrapper);
                if (null != reportDashboard) {
                    reportDashboard.setId(null);
                    reportDashboard.setReportCode(copyReportCode);
                    reportDashboardService.save(reportDashboard);
                }

                //查询组件
                QueryWrapper<ReportDashboardWidget> widthWrapper = new QueryWrapper();
                wrapper.eq("report_code",reportCode);
                List<ReportDashboardWidget> reportDashboardWidgetList = reportDashboardWidgetService.list(widthWrapper);
                if (!CollectionUtils.isEmpty(reportDashboardWidgetList)) {
                    String finalCopyReportCode = copyReportCode;
                    reportDashboardWidgetList.forEach(reportDashboardWidget -> {
                        reportDashboardWidget.setId(null);
                        reportDashboardWidget.setReportCode(finalCopyReportCode);
                    });
                    reportDashboardWidgetService.saveBatch(reportDashboardWidgetList);
                }

                break;
            case report_excel:
//                ReportExcel reportExcel = reportExcelService.selectOne("report_code", reportCode);
//                if (null != reportExcel) {
//                    reportExcel.setId(null);
//                    reportExcel.setReportCode(copyReportCode);
//                    reportExcelService.insert(reportExcel);
//                }

                break;
            default:
        }
    }

    @Override
    public ReportDashboardObjectDto getByCode(String code) {
        return null;
    }

//    @Override
//    public ReportDashboardObjectDto getByCode(String code) {
//        ReportDashboardObjectDto result = new ReportDashboardObjectDto();
//        ReportDashboardDto reportDashboardDto = new ReportDashboardDto();
//        LambdaQueryWrapper wrapper = new LambdaQueryWrapper();
//        wrapper.eq(ReportDashboard::getReportCode,code);
//        ReportDashboard reportDashboard = getOne()
//        if (null == reportDashboard) {
//            return new ReportDashboardObjectDto();
//        }
//        GaeaBeanUtils.copyAndFormatter(reportDashboard, reportDashboardDto);
//
//        List<ReportDashboardWidget> list = reportDashboardWidgetService.list(
//                new QueryWrapper<ReportDashboardWidget>().lambda()
//                        .eq(ReportDashboardWidget::getReportCode, reportCode)
//                        .orderByAsc(ReportDashboardWidget::getSort)
//        );
//        List<ReportDashboardWidgetDto> reportDashboardWidgetDtoList = new ArrayList<>();
//        list.forEach(reportDashboardWidget -> {
//            ReportDashboardWidgetDto reportDashboardWidgetDto = new ReportDashboardWidgetDto();
//            ReportDashboardWidgetValueDto value = new ReportDashboardWidgetValueDto();
//            value.setSetup(org.apache.commons.lang3.StringUtils.isNotBlank(reportDashboardWidget.getSetup()) ? JSONObject.parseObject(reportDashboardWidget.getSetup()) : new JSONObject());
//            value.setData(org.apache.commons.lang3.StringUtils.isNotBlank(reportDashboardWidget.getData()) ? JSONObject.parseObject(reportDashboardWidget.getData()) : new JSONObject());
//            value.setPosition(org.apache.commons.lang3.StringUtils.isNotBlank(reportDashboardWidget.getPosition()) ? JSONObject.parseObject(reportDashboardWidget.getPosition()) : new JSONObject());
//            value.setCollapse(StringUtils.isNotBlank(reportDashboardWidget.getCollapse()) ? JSONObject.parseObject(reportDashboardWidget.getCollapse()) : new JSONObject());
//
//            //实时数据的替换
//            analysisData(value);
//            reportDashboardWidgetDto.setType(reportDashboardWidget.getType());
//            reportDashboardWidgetDto.setValue(value);
//            reportDashboardWidgetDto.setOptions(JSONObject.parseObject(reportDashboardWidget.getOptions()));
//            reportDashboardWidgetDtoList.add(reportDashboardWidgetDto);
//        });
//        reportDashboardDto.setWidgets(reportDashboardWidgetDtoList);
//        result.setDashboard(reportDashboardDto);
//        result.setReportCode(reportCode);
//        return result;
//    }



    private Report copyReport(Report report, ReportDto dto){
        //复制主表数据
        Report copyReport = new Report();
//        GaeaBeanUtils.copyAndFormatter(report, copyReport);
        copyReport.setReportCode(dto.getReportCode());
        copyReport.setReportName(dto.getReportName());
        return copyReport;
    }

//    @Override
//    public void processBeforeOperation(Report entity, BaseOperationEnum operationEnum) throws BusinessException {
//
//    }
}
