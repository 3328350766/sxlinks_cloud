package com.sxlinks.modules.system.zlm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件 user-settings 映射的配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "user-settings", ignoreInvalidFields = true)
public class UserSetting {

    private String serverId = "000000";

}
