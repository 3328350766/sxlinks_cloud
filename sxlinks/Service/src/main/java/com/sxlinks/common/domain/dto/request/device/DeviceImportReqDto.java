package com.sxlinks.common.domain.dto.request.device;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DevicceImportReqDto.java, v 0.1 2021-01-05  Exp $$
  */
@Data
public class DeviceImportReqDto {
    String deviceCode;
    String productCode;
    String deviceName;
    String deviceSecret;
}
