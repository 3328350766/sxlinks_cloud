package com.sxlinks.modules.system.service.impl.report;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.modules.system.entity.report.ReportDashboardWidget;
import com.sxlinks.modules.system.mapper.ReportDashboardWidgetMapper;
import com.sxlinks.modules.system.service.report.ReportDashboardWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @desc ReportDashboardWidget 大屏看板数据渲染服务实现
* @author Raod
* @date 2021-04-12 15:12:43.724
**/
@Service
public class ReportDashboardWidgetServiceImpl extends ServiceImpl<ReportDashboardWidgetMapper,ReportDashboardWidget> implements ReportDashboardWidgetService {

    @Autowired
    private ReportDashboardWidgetMapper reportDashboardWidgetMapper;

    @Override
    public ReportDashboardWidget getDetail(Long id) {
        ReportDashboardWidget reportDashboardWidget = this.getById(id);
        return reportDashboardWidget;
    }
}
