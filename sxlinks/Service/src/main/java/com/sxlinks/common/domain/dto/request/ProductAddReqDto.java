package com.sxlinks.common.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: ProductResDto.java, v 0.1 2020-12-15  Exp $$
  */
@Data
public class ProductAddReqDto {
    @NotNull(message = "产品名不能为空")
    private String       productName;
    @NotNull(message = "数据协议不能为空")
    private String protocolCode;
    @NotNull(message = "节点类型不能为空")
    private String nodeType;
    private String       productDesc;
    @NotNull(message = "下行网络协议不能为空")
    private String transportList;

}
