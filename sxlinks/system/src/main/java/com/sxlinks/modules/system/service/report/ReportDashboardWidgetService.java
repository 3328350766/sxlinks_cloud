
package com.sxlinks.modules.system.service.report;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.ReportDashboardWidget;

/**
* @desc ReportDashboardWidget 大屏看板数据渲染服务接口
* @author Raod
* @date 2021-04-12 15:12:43.724
**/
public interface ReportDashboardWidgetService extends IService<ReportDashboardWidget> {

    /***
     * 查询详情
     *
     * @param id
     */
    ReportDashboardWidget getDetail(Long id);
}
