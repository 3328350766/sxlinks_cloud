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
@ApiModel(value = "小程序用户基本信息")
public class MiniUserRespVo implements Serializable {

    @ApiModelProperty(value = "手机号", example = "13000900900")
    private String phone;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

}
