package com.sxlinks.common.domain.dto.request.upgrade;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * com.sxlinks.common.domain.dto.request.upgrade
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/15
 */
@Data
public class UpgradeDeviceQueryReqDto {
    private String deviceCode;
    private String deviceName;
    @NotNull(message = "固件ID不能为空")
    private Long firmwareId;
}
