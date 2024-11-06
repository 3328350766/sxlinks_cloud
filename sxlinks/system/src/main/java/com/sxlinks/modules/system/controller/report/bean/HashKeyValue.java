package com.sxlinks.modules.system.controller.report.bean;



import com.sxlinks.modules.system.controller.report.annotation.HashKey;

import java.io.Serializable;

/**
 * hash键值对
 * @author lr
 * @since 2021-01-12
 *
 **/
public class HashKeyValue implements Serializable {

    private String key;
    private String value;

    private HashKey hashKey;

    public boolean nonNull(){
        return key != null && value != null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashKey getHashKey() {
        return hashKey;
    }

    public void setHashKey(HashKey hashKey) {
        this.hashKey = hashKey;
    }
}
