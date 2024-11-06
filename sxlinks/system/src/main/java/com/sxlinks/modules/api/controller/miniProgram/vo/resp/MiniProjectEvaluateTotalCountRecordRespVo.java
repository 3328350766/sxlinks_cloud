package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序评估记录总汇总表统计信息")
public class MiniProjectEvaluateTotalCountRecordRespVo extends MiniProjectEvaluateCountRecordRespVo implements Serializable {

    @ApiModelProperty(value = "地区", example = "汶川县")
    private String region;

    @ApiModelProperty(value = "核报灾损数量", example = "10")
    private int reportNum;

    @ApiModelProperty(value = "评估进度", example = "30")
    private int progressRatio;

}
