package com.sxlinks.common.domain.dto.response;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: ProtocolItemResDto.java, v 0.1 2020-12-15  Exp $$
  */
@Data
public class DataTypeItemResDto {
    String code;
    String name;
    String desc;

    public DataTypeItemResDto(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
