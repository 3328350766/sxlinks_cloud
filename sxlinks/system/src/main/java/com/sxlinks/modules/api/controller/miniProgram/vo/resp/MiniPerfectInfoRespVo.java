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
@ApiModel(value = "小程序最后一次填写/完善用户信息")
public class MiniPerfectInfoRespVo implements Serializable {

    @ApiModelProperty(value = "用户角色(2:当地住建联系人、3:当地联系人、5:专家)", example = "2")
    private Integer userType;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String realname;

    @ApiModelProperty(value = "年龄", example = "29")
    private Integer age;

    @ApiModelProperty(value = "单位", example = "天津天津市河西建设路677号")
    private String unit;

    @ApiModelProperty(value = "专业", example = "技术工")
    private String major;

    @ApiModelProperty(value = "职务", example = "技术员")
    private String post;

    @ApiModelProperty(value = "手写签名", example = "xxx")
    private String autograph;
}
