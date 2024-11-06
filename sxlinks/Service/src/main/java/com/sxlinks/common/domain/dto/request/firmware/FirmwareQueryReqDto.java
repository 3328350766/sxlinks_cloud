package com.sxlinks.common.domain.dto.request.firmware;

import lombok.Data;

/**
 * com.sxlinks.common.domain.dto.request.firmware
 * project bytecub
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/8
 */
@Data
public class FirmwareQueryReqDto {
    private String productCode;
    private String firmwareName;
}
