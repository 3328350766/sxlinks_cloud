package com.sxlinks.common.metadata;

import lombok.Getter;

/**
 * 产品物模型的类型
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: ProductFuncTypeEnum.java, v 0.1 2020-12-11  Exp $$
  */
@Getter
public enum DeviceModelTypeEnum {
    PROP("PROP", "属性"),
    FUNC("FUNC", "功能"),
    EVENT("EVENT", "事件"),
    SERVICE("SERVICE", "服务");
    String type;
    String msg;

    DeviceModelTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static DeviceModelTypeEnum explain(String type){
        for(DeviceModelTypeEnum item: DeviceModelTypeEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return DeviceModelTypeEnum.PROP;
    }
}
