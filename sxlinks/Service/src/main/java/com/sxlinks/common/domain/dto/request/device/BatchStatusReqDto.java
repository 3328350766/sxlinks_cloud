package com.sxlinks.common.domain.dto.request.device;

import lombok.Data;

import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: BatchStatusReqDto.java, v 0.1 2020-12-23  Exp $$
  */
@Data
public class BatchStatusReqDto {
    /**list内容是主键ID*/
    List<Long> list;
    String     type;
}
