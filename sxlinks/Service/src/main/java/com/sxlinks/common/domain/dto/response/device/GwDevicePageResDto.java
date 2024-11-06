package com.sxlinks.common.domain.dto.response.device;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: GwDevicePageResDto.java, v 0.1 2021-02-01  Exp $$
  */
@Data
public class GwDevicePageResDto extends DevicePageResDto {
    long deviceTotal;
    long deviceActive;
}
