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
@ApiModel(value = "小程序评估记录每日汇总表统计信息")
public class MiniProjectEvaluateDayCountRecordRespVo extends MiniProjectEvaluateCountRecordRespVo implements Serializable {

    @ApiModelProperty(value = "组名", example = "汶川县")
    private String groupName;

    @ApiModelProperty(value = "评估范围", example = "汶川县")
    private String scope;

    @ApiModelProperty(value = "评估总数", example = "10")
    private int totalNum;
}
