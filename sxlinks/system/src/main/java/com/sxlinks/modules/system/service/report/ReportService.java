package com.sxlinks.modules.system.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.Report;
import com.sxlinks.modules.system.entity.report.ReportDashboardObjectDto;
import com.sxlinks.modules.system.entity.report.ReportDto;

/**
 *
 * @author chenkening
 * @date 2021/3/26 10:35
 */
public interface ReportService extends IService<Report> {

    /**
     * 下载次数+1
     * @param reportCode
     */
    void downloadStatistics(String reportCode);

    /**
     * 复制大屏
     * @param dto
     */
    void copy(ReportDto dto);

    ReportDashboardObjectDto getByCode(String code);
}
