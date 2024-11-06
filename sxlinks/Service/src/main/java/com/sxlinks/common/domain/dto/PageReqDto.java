package com.sxlinks.common.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: PageDto.java, v 0.1 2020-12-15  Exp $$
  */
@Data
public class PageReqDto<T> implements Serializable {

    private static final long serialVersionUID = -3214634852095029897L;
    private int pageNo = 1;
    private int          limit = 20;
    private              T paramData;

}

