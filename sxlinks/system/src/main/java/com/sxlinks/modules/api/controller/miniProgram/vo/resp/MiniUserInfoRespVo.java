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
@ApiModel(value = "小程序用户信息")
public class MiniUserInfoRespVo implements Serializable {

    @ApiModelProperty(value = "用户类型[0:超管、1:负责人、2:当地住建联系人、3:当地联系人、4:组长、5:专家],如果类型为空则跳转到填写信息页面", example = "0")
    private Integer userType;

    @ApiModelProperty(value = "手机号", example = "13000900900")
    private String phone;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "年龄", example = "29")
    private Integer age;

    @ApiModelProperty(value = "通知数量", example = "2")
    private int num;

    @ApiModelProperty(value = "当前评估任务状态(0:待审核/待分组 1:已通过 2:已拒绝 3:已结束)", example = "2")
    private Integer status;

    @ApiModelProperty(value = "项目ID", example = "2")
    private Long projectId;

    @ApiModelProperty(value = "项目名称", example = "大地震")
    private String projectName;

    @ApiModelProperty(value = "单位", example = "天津天津市河西建设路677号")
    private String unit;

    @ApiModelProperty(value = "专业", example = "技术")
    private String major;

    @ApiModelProperty(value = "职务", example = "技术员")
    private String post;

    @ApiModelProperty(value = "签名", example = "")
    private String autograph;

}
