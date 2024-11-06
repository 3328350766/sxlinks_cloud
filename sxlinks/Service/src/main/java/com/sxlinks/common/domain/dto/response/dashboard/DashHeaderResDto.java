package com.sxlinks.common.domain.dto.response.dashboard;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: DashHeaderResDto.java, v 0.1 2021-01-27  Exp $$
  */
@Data
public class DashHeaderResDto {
    Integer deviceCount;
    Integer deviceActive;
    Integer deviceEnable;
    Integer msgCount;
}
