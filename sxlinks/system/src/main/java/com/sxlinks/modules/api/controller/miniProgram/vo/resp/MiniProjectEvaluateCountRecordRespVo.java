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
@ApiModel(value = "小程序评估记录统计信息")
public class MiniProjectEvaluateCountRecordRespVo implements Serializable {


    @ApiModelProperty(value = "禁止使用数量", example = "30")
    private int prohibitNum;

    @ApiModelProperty(value = "禁止使用占比", example = "10")
    private int prohibitRatio;

    @ApiModelProperty(value = "限制使用数量", example = "30")
    private int limitNum;

    @ApiModelProperty(value = "限制使用占比", example = "30")
    private int limitRatio;

    @ApiModelProperty(value = "可以使用数量", example = "30")
    private int useNum;

    @ApiModelProperty(value = "可以使用占比", example = "30")
    private int useRatio;




}
