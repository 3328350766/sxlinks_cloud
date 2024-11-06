package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Data
@ApiModel(value = "小程序填写/完善用户参数信息")
public class MiniPerfectInfoReqVo implements Serializable {

    @ApiModelProperty(value = "用户角色(2:当地住建联系人、3:当地联系人、5:专家)", example = "2")
    @NotNull(message = "用户角色不能为空")
    private Integer userType;

    @ApiModelProperty(value = "地震ID", example = "2")
    @NotNull(message = "地震ID不能为空")
    private Long projectId;

    @ApiModelProperty(value = "姓名", example = "张三")
    @NotBlank(message = "姓名不能为空")
    @Length(max = 10, message = "姓名长度不能大于30")
    private String realname;

    @ApiModelProperty(value = "年龄", example = "29")
    private Integer age;

    @ApiModelProperty(value = "单位", example = "天津天津市河西建设路677号")
    private String unit;

    @ApiModelProperty(value = "专业", example = "技术工")
    private String major;

    @ApiModelProperty(value = "职务", example = "技术员")
    @NotBlank(message = "职务不能为空")
    @Length(max = 30, message = "职务长度不能大于30")
    private String post;

    @ApiModelProperty(value = "手写签名", example = "xxx")
    private String autograph;
}
