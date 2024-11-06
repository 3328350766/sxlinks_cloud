package com.sxlinks.common.biz;

import com.sxlinks.common.constants.SXConstants;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2021 All Rights Reserved.
  * 
  * @author baba
  * @version Id: MqttUtil.java, v 0.1 2021-01-05  Exp $$
  */
public class MqttUtil {
    /**从mqtt connect的userName中获取deviceCode*/
    public static  String fetchDeviceCode(String userName){
        String[] strings =  userName.split("\\" + SXConstants.AUTH.SIGN_SPLIT);
        return strings[0];
    }
}
