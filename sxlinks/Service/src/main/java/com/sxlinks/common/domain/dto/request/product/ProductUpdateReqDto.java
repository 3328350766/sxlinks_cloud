package com.sxlinks.common.domain.dto.request.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: ProductDetailResDto.java, v 0.1 2020-12-16  Exp $$
  */
@Data
public class ProductUpdateReqDto {
    private Long id;
    @NotNull(message = "产品编码不能为空")
    private String productCode;
    @NotNull(message = "产品名不能为空")
    private String productName;
    @NotNull(message = "下行网络协议不能为空")
    private String transportList;
    @NotNull(message = "数据协议不能为空")
    private String protocolCode;
    @NotNull(message = "节点类型不能为空")
    private String nodeType;
    private String nodeName;
    private String productDesc;
    private Integer productStatus;
    /**协议开始字段*/
    private String protocolName;
    private Integer protocolType;

}
