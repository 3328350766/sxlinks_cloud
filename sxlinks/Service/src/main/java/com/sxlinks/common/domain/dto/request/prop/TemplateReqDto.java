package com.sxlinks.common.domain.dto.request.prop;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: TemplateReqDto.java, v 0.1 2021-01-19  Exp $$
  */
@Data
public class TemplateReqDto {
    @NotNull(message = "产品不能为空")
    String productCode;
    String deviceCode;  //设备编码
    String identifier;
    @NotNull(message = "功能类型不能为空")
    String funcType;
}
