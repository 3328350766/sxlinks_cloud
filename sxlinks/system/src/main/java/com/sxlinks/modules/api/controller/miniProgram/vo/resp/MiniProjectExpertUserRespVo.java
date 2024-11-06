package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "小程序地震项目专家组员基本信息")
public class MiniProjectExpertUserRespVo extends MiniUserRespVo implements Serializable {

    @ApiModelProperty(value = "状态(0:已分配到其他组 1:正常 2:请假中 3:已请假)", example = "1")
    private Integer status;

}
