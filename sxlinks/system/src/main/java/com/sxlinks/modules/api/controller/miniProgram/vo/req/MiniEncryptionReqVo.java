package com.sxlinks.modules.api.controller.miniProgram.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/20
 */
@Data
@ApiModel(value = "小程序用户密文参数信息")
public class MiniEncryptionReqVo implements Serializable {
//    @ApiModelProperty(value = "微信密文", required = true, example = "1")
//    @NotBlank(message = "微信密文不能为空")
//    private String encryptedData;
//
//    @ApiModelProperty(value = "微信密文向量", required = true, example = "1")
//    @NotBlank(message = "微信密文向量不能为空")
//    private String iv;
//
//    @ApiModelProperty(value = "临时TOKEN", required = true, example = "1")
//    @NotBlank(message = "临时TOKEN不能为空")
//    private String temporaryToken;

    @ApiModelProperty(value = "code", required = true, example = "12121")
    @NotBlank(message = "临时code不能为空")
    private String code;

    @ApiModelProperty(value = "项目ID(上次选择)", example = "1")
    private Long projectId;
}
