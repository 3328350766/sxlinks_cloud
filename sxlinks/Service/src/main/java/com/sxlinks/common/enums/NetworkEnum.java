package com.sxlinks.common.enums;

import com.sxlinks.common.exception.BCGException;
import lombok.Getter;

/**传输协议类型枚举
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: NetworkEnum.java, v 0.1 2020-12-09  Exp $$
  */
@Getter
public enum NetworkEnum {
    MQTT("MQTT", "MQTT协议"),
    UDP("UDP", "UDP");
    String type;
    String msg;

    NetworkEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static NetworkEnum explain(String type){
        for(NetworkEnum item:NetworkEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return null;
    }

    public static NetworkEnum explainMust(String type){
        for(NetworkEnum item:NetworkEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        throw BCGException.genException(BCErrorEnum.INVALID_NETWORK , type);
    }
}
