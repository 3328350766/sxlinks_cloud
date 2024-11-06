package com.sxlinks.plugin.es.config.enums;

import lombok.Getter;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: BcMetaType.java, v 0.1 2020-12-07  Exp $$
  */
@Getter
public enum BcEsMetaType {
    KEYWORD("keyword", ""),
    TEXT("text", ""),
    STRING("keyword", ""),
    LONG("long",""),
    DOUBLE("double", ""),
    FLOAT("float", ""),
    DATE("date", ""),
    BOOLEAN("boolean",""),
    INTEGER("integer",""),
    GEOPOINT("geo_point ", ""),
    OBJECT("object", ""),
    UNKNOWN("unknown ", ""),
    BINARY("binary", "二进制");
    String type;
    String msg;

    BcEsMetaType(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
