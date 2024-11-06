package com.sxlinks.common.domain.gateway.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DeviceActiveMqBo.java, v 0.1 2021-01-05  Exp $$
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceActiveMqBo {
    String deviceCode;
    Boolean active;
    String host;
    Integer port;
    Date timestamp;
}
