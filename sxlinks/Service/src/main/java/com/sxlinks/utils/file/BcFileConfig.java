package com.sxlinks.utils.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.sxlinks.utils.file
 * project bytecub  bytecub.cn
 *
 * @author baba 3328350766@qq.com
 * @date 2021/4/8
 */
@Component
@ConfigurationProperties(prefix = "bytecub")
public class BcFileConfig {
    /** 上传路径 */
    private static String profile;
    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        BcFileConfig.profile = profile;
    }
}
