package com.sxlinks.common.domain.bo;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: PropAttrBo.java, v 0.1 2020-12-17  Exp $$
  */
@Data
public class FunctionAttrBo {
    String dataType;
    String identifier;
    Long max;
    Long min;
    String unit;
    String unitName;
    String bool0;
    String bool1;
    Long length;
    Object data;
}
