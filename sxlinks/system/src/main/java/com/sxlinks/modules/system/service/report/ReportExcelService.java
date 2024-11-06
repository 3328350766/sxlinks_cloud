package com.sxlinks.modules.system.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.ReportDto;
import com.sxlinks.modules.system.entity.report.ReportExcel;
import com.sxlinks.modules.system.entity.report.ReportExcelDto;

/**
 * TODO
 *
 * @author chenkening
 * @date 2021/4/13 15:14
 */
public interface ReportExcelService extends IService<ReportExcel> {

    /**
     * 根据报表编码查询详情
     *
     * @param reportCode
     * @return
     */
    ReportExcelDto detailByReportCode(String reportCode);

    /**
     * 报表预览
     *
     * @param reportExcelDto
     * @return
     */
    ReportExcelDto preview(ReportExcelDto reportExcelDto);

    /**

     */
    void copy(ReportExcelDto reportExcelDto);
    /**
     * 导出为excel
     *
     * @param reportExcelDto
     * @return
     */
    Boolean exportExcel(ReportExcelDto reportExcelDto);

//    Boolean exportPdf(ReportExcelDto reportExcelDto);
}
