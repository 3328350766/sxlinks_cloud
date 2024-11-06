package com.sxlinks.modules.api.controller.miniProgram.vo.req;

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
@ApiModel(value = "小程序环境设施评估选项参数信息")
public class MiniProjectEvaluateFacilityOptionReqVo implements Serializable {

    @ApiModelProperty(value = "选项ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "选项勾选状态(0:否 1:是)", example = "1")
    private Integer status;
}
