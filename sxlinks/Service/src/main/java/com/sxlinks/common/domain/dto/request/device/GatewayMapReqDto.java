package com.sxlinks.common.domain.dto.request.device;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 子设备关联到网关设备
  * @author baba
  * @version Id: GatewayMapReqDto.java, v 0.1 2021-02-01  Exp $$
  */
@Data
public class GatewayMapReqDto {
    @NotNull(message = "网关设备编码不能为空")
    String       gwDeviceCode;
    @NotEmpty(message = "子设备列表不能为空")
    List<String> devices;
    Boolean removeAction = false;
}
