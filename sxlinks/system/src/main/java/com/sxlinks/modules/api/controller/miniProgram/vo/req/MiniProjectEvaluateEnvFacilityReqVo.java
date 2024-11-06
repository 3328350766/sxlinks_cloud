package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序环境设施评估参数信息")
public class MiniProjectEvaluateEnvFacilityReqVo extends MiniProjectEvaluateFacilityReqVo implements Serializable {

    @ApiModelProperty(value = "检查情况", example = "[]")
    private List<MiniProjectEvaluateFacilityOptionReqVo> inspectOpinionList;

}
