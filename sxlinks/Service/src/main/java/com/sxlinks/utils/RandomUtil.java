package com.sxlinks.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
  * sxlinks.com.
  * Copyright (c) 2020-2020 All Rights Reserved.
  * 
  * @author baba
  * @version Id: RandomUtil.java, v 0.1 2020-12-23  Exp $$
  */
public class RandomUtil {

    public static String randomString(int length){
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}
