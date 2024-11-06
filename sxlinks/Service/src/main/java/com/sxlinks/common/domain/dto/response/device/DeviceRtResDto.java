package com.sxlinks.common.domain.dto.response.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DeviceRtResDto.java, v 0.1 2020-12-24  Exp $$
  */
@Data
public class DeviceRtResDto {
    String propName;
    String identifier;
    Object value = "/";
    String unit;
    String unitName;
    String dataType;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Object   arrivedTime;
}
