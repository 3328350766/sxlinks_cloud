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
@ApiModel(value = "小程序公共建筑评估记录信息")
public class MiniProjectEvaluateBuildingRecordRespVo extends MiniProjectEvaluateRecordRespVo implements Serializable {

    @ApiModelProperty(value = "建筑名称", example = "映秀镇中学")
    private String buildingName;

}
