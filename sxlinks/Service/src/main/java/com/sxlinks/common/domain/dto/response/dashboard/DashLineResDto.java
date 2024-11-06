package com.sxlinks.common.domain.dto.response.dashboard;

import lombok.Data;

import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DashLineResDto.java, v 0.1 2021-01-27  Exp $$
  */
@Data
public class DashLineResDto {
    List<String> dates;
    List<Long> count;
}
