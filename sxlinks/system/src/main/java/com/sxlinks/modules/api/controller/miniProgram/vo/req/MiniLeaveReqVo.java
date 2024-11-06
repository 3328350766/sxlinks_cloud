package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/22
 */
@Data
@ApiModel(value = "小程序申请请假参数信息")
public class MiniLeaveReqVo implements Serializable {

    @ApiModelProperty(value = "小组ID", example = "1")
    @NotNull(message = "小组ID不能为空")
    private Long groupId;

    @ApiModelProperty(value = "请假原因", example = "感冒病假")
    @NotBlank(message = "请假原因不能为空")
    private String reason;

}
