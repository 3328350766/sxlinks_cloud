package com.sxlinks.plugin.es.domain;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: EsRange.java, v 0.1 2020-12-27  Exp $$
  */
@Data
public class EsRange<M,N> {
    M max;
    N min;
    String field;
}
