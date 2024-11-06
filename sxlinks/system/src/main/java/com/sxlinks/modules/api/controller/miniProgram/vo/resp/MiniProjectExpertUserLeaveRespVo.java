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
@ApiModel(value = "小程序地震项目专家组员请假信息")
public class MiniProjectExpertUserLeaveRespVo extends MiniUserRespVo implements Serializable {

    @ApiModelProperty(value = "请假原因", example = "感冒病假")
    private String reason;

    @ApiModelProperty(value = "审批状态(0:待审核 1:已通过 2:已拒绝)", example = "1")
    private Integer status;

}
