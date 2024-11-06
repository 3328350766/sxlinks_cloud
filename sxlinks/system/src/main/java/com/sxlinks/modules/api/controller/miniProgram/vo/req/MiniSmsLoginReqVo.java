package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2021/9/27
 */
@Data
@ApiModel(value = "小程序手机短信验证码登录参数信息")
public class MiniSmsLoginReqVo implements Serializable {

    @ApiModelProperty(value = "code", required = true, example = "123456")
    @NotBlank(message = "短信验证码不能为空")
    private String code;

    @ApiModelProperty(value = "手机号", required = true, example = "18888888888")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "项目ID(上次选择)", example = "1")
    private Long projectId;
}
