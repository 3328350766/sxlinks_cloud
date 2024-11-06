package com.sxlinks.common.domain.dto.request.product;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: ProductQueryReqDto.java, v 0.1 2021-02-01  Exp $$
  */
@Data
public class ProductQueryReqDto {
    String nodeType;
    String productCode;
    String productName;
}
