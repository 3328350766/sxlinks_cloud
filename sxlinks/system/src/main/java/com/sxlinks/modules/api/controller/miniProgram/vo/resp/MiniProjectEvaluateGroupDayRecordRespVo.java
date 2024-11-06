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
@ApiModel(value = "小程序评估记录小组每日汇总信息")
public class MiniProjectEvaluateGroupDayRecordRespVo implements Serializable {

    @ApiModelProperty(value = "户主姓名或公建名称或市政设施名称", example = "张晓刚")
    private String name;

    @ApiModelProperty(value = "评估结果选项(0:禁止使用 1:可以使用 2:限制使用)", example = "1")
    private Integer evaluateResult;

}
