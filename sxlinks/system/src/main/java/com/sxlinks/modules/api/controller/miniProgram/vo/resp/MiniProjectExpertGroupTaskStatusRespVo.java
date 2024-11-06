package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/21
 */
@Data
@ApiModel(value = "小程序地震项目专家组评估任务完成状态信息")
public class MiniProjectExpertGroupTaskStatusRespVo implements Serializable {

    @ApiModelProperty(value = "范围ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "范围CODE", example = "1")
    private String scopeCode;

    @ApiModelProperty(value = "范围名称", example = "红星社区大龙村")
    private String scopeName;

    @ApiModelProperty(value = "状态(0:未完成 1:已完成)", example = "1")
    private int status;

}
