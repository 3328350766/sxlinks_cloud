package com.sxlinks.common.domain.dto.request.device;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DevCreateReqDto.java, v 0.1 2020-12-22  Exp $$
  */
@Data
public class DeviceUpdateReqDto {
    Long  id;
    String deviceName ;
    String gwDevCode ;
    String productCode ;
    String deviceCode  ;
    String deviceSecret  ;
    String firmwareVersion;
}
