
package com.sxlinks.modules.system.controller.report;

import com.sxlinks.modules.system.controller.report.bean.ResponseBean;
import com.sxlinks.modules.system.controller.report.param.DataSetParamValidationParam;
import com.sxlinks.modules.system.entity.report.DataSetParamDto;
import com.sxlinks.modules.system.service.report.DataSetParamService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @desc 数据集动态参数 controller
* @website https://gitee.com/anji-plus/gaea
* @author Raod
* @date 2021-03-18 12:12:33.108033200
**/
@RestController
@Api(tags = "数据集动态参数管理")
@RequestMapping("/dataSetParam")
public class DataSetParamController {

    @Autowired
    private DataSetParamService dataSetParamService;


    /**
     * 测试 查询参数是否正确
     * @param param
     * @return
     */
    @PostMapping("/verification")
    public ResponseBean verification(@Validated @RequestBody DataSetParamValidationParam param) {
        DataSetParamDto dto = new DataSetParamDto();
        dto.setSampleItem(param.getSampleItem());
        dto.setValidationRules(param.getValidationRules());
        return ResponseBean.builder().data(dataSetParamService.verification(dto)).build();
    }
}
