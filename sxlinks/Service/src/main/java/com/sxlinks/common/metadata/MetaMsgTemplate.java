package com.sxlinks.common.metadata;

import lombok.Data;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: MetaMsgTemplate.java, v 0.1 2020-12-07  Exp $$
  */
@Data
public class MetaMsgTemplate {
    String code;
    String name;
    String desc;
    //BcMetaType type;

    public MetaMsgTemplate(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;

    }
}
