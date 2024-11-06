package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/6/3
 */
@Data
@ApiModel(value = "小程序地震范围信息")
public class MiniScopeInfoRespVo implements Serializable {

    @ApiModelProperty(value = "范围CODE", example = "1")
    private String scopeCode;

    @ApiModelProperty(value = "范围名称", example = "红星社区大龙村")
    private String scopeName;

}
