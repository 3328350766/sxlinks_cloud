package com.sxlinks.common.domain.dto.request.device;

import lombok.Data;

import java.util.List;

/**
 *  * sxlinks.com.
 *  * Copyright (c) 2020-2021 All Rights Reserved.
 *  * 批量查询设备最新数据
 *  * @author baba
 *  * @Date 2021/3/31  Exp $$
 *  
 */
@Data
public class QueryDevsRtReqDto {
    List<String> deviceCodes;
    String productCode;
    String type;
}
