package com.sxlinks.plugin.es.config.enums;

import lombok.Getter;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: KeyQueryTypeEnum.java, v 0.1 2020-12-27  Exp $$
  */
@Getter
public enum KeyMatchTypeEnum {
    EXIST("exist", "需要精准比较值"),
    NOT_EXIST("exist", "只需要判断值不为null");
    String type;
    String msg;

    KeyMatchTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static KeyMatchTypeEnum explain(String type){
        for(KeyMatchTypeEnum item:KeyMatchTypeEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return KeyMatchTypeEnum.EXIST;
    }
}
