package com.sxlinks.common.domain.dto.request;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DevQueryReqDto.java, v 0.1 2020-12-22  Exp $$
  */
@Data
public class DevQueryReqDto {
    String deviceName;
    String gwDevCode;
    String deviceCode;
    String productCode;
    Integer enableStatus;
    Integer activeStatus;
    String  nodeType;
    /**
     * 是否查询的是网关子设备
     *
     * */
    Boolean subDevQuery = false;


}
