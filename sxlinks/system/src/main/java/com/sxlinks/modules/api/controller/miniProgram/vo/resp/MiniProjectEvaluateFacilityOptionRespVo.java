package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序环境设施评估选项信息")
public class MiniProjectEvaluateFacilityOptionRespVo implements Serializable {

    @ApiModelProperty(value = "选项ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "选项名称", example = "垃圾堆体是否损坏")
    private String name;
}