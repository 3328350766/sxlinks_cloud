package com.sxlinks.common.domain.dto.request;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DevBatchAddReqDto.java, v 0.1 2020-12-22  Exp $$
  */
@Data
public class DevBatchAddReqDto {
    Integer number;
    String productCode = "";
    String gwDevCode = "";
    String deviceName = "";
}
