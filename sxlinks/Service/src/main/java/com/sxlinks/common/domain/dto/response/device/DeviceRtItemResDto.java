package com.sxlinks.common.domain.dto.response.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DeviceRtItemResDto.java, v 0.1 2020-12-27  Exp $$
  */
@Data
public class DeviceRtItemResDto {
    String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    Date   arrivedTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    Date   devTime;
    Object value;
    String unit;
}
