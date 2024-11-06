package com.sxlinks.modules.api.controller.miniProgram.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Data
@ApiModel(value = "小程序登录信息")
public class MiniLoginRespVo extends MiniUserInfoRespVo implements Serializable {

    @ApiModelProperty(value = "token", example = "xxx.xxx.xxx")
    private String token;

}
