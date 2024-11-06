
package com.sxlinks.modules.system.service.report;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxlinks.modules.system.entity.report.ReportShare;
import com.sxlinks.modules.system.entity.report.ReportShareDto;

/**
* @desc ReportShare 报表分享服务接口
* @author Raod
* @date 2021-08-18 13:37:26.663
**/
public interface ReportShareService extends IService<ReportShare> {

    /***
     * 查询详情
     *
     * @param
     * @return
     */
//    ReportShare getDetail(Long id);

    ReportShareDto insertShare(ReportShareDto dto);

//    ReportShare detailByCode(String shareCode);
//
//    /**
//     * 延期过期时间
//     * @param dto
//     */
//    void shareDelay(ReportShareDto dto);
}
