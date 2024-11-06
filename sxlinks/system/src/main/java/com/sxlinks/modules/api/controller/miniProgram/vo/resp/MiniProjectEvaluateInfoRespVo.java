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
@ApiModel(value = "小程序市政设施评估记录信息")
public class MiniProjectEvaluateInfoRespVo extends MiniProjectEvaluateRecordRespVo implements Serializable {

    @ApiModelProperty(value = "评估对象类型(1:单户 2:多户 3:公共建筑 4:填埋场 5:焚烧场 6:垃圾中转站 7:城市公厕 8:排水设施 9:供水设施 10:燃气设施 11:市政基础设施)", example = "1")
    private Integer type;

    @ApiModelProperty(value = "户主", example = "张三")
    private String ownerName;

    @ApiModelProperty(value = "业主身份证", example = "420000198001028901")
    private String ownerIdCard;

    @ApiModelProperty(value = "户数", example = "100")
    private Integer num;

    @ApiModelProperty(value = "建筑名称", example = "映秀镇中学")
    private String name;

    @ApiModelProperty(value = "建筑概况", example = "楼房3层,3x3结构,已居住5年")
    private String info;

    @ApiModelProperty(value = "检查意见", example = "地震导致大梁出现裂缝")
    private String inspectOpinion;

    @ApiModelProperty(value = "图片链接(多张使用英文逗号(xxx,xxx)分割)", example = "https://www.baidu.com/a.jpg")
    private String imgUrls;

    @ApiModelProperty(value = "评估结果选项(0:禁止使用 1:可以使用 2:限制使用)", example = "1")
    private Integer evaluateResult;

    @ApiModelProperty(value = "评估意见", example = "大梁加固后可以继续居住")
    private String evaluateOpinion;

    @ApiModelProperty(value = "手写签名", example = "xxx")
    private String autograph;


}
