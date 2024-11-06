package com.sxlinks.modules.api.controller.miniProgram.vo.req;

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
@ApiModel(value = "小程序公共设施评估参数信息")
public class MiniProjectEvaluatePublicFacilityReqVo extends MiniProjectEvaluateFacilityReqVo implements Serializable {

    @ApiModelProperty(value = "检查情况", example = "[]")
    private String inspectOpinion;

    @ApiModelProperty(value = "图片链接(多张使用英文逗号(xxx,xxx)分割)", example = "https://www.baidu.com/a.jpg")
    private String imgUrls;

}
