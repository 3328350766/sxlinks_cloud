package com.sxlinks.common.biz;

import com.sxlinks.common.constants.SXConstants;
import com.sxlinks.common.metadata.ProductModelTypeEnum;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: RedisKeyUtil.java, v 0.1 2020-12-25  Exp $$
  */
public class RedisKeyUtil {
    /**
     * 构造设备实时状态缓存key
     *
     * */
    public static String buildRtCacheKey( String deviceCode, ProductModelTypeEnum funcType){
        return SXConstants.REDIS_KEY.DEV_RT_DATA + deviceCode + ":" + funcType.getType();
    }
    public static String buildRtCacheKey( String deviceCode, String funcType){
        return SXConstants.REDIS_KEY.DEV_RT_DATA + deviceCode + ":" + funcType;
    }
    public static String buildDeviceInfoKey( String deviceCode){
        return SXConstants.REDIS_KEY.DEV_INFO + deviceCode;
    }

    public static String buildDeviceOfflineKey( String deviceCode){
        return SXConstants.REDIS_KEY.DEVICE_OFFLINE + deviceCode;
    }

    public static String buildProductInfoKey( String productCode){
        return SXConstants.REDIS_KEY.PRODUCT_INFO + productCode;
    }

    public static String buildAuthKey( String key){
        return SXConstants.REDIS_KEY.OPEN_AUTH_INFO + key;
    }
}
