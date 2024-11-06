package com.sxlinks.common.domain.dto.request.firmware;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * com.sxlinks.common.domain.dto.request.firmware
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/8
 */
@Data
public class FirmwareCreateReqDto {
    @NotNull(message = "未上传附件")
    private String url;
    @NotNull(message = "固件名不能为空")
    private String name;
    @NotNull(message = "版本号不能为空")
    private String version;
    @NotNull(message = "序列号不能为空")
    private Integer seqNo;
    @NotNull(message = "请选择产品")
    private String productCode;
    @NotNull(message = "文件签名不能为空")
    private String signCode;

}
