package com.sxlinks.modules.system.util.sms;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/24
 */
@Component
@ConfigurationProperties("sms")
public class SmsProperties {

    /**
     * 阿里云信配置信息
     */
    private final AliSms aliSms = new AliSms();
    /**
     * 是否生成环境-默认开发环境
     */
    private boolean prodMode = false;
    /**
     * 是否开启LOG-默认开启
     */
    private boolean consoleEnabled = true;
    /**
     * 用户每天发送短信限制数量
     */
    private int userDayNum = 10;

    /**
     * 验证码失效时间(秒)-默认5分钟
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration expires = Duration.ofSeconds(5 * 60);

    public boolean isProdMode() {
        return prodMode;
    }

    public void setProdMode(boolean prodMode) {
        this.prodMode = prodMode;
    }

    public boolean isConsoleEnabled() {
        return consoleEnabled;
    }

    public void setConsoleEnabled(boolean consoleEnabled) {
        this.consoleEnabled = consoleEnabled;
    }

    public Duration getExpires() {
        return expires;
    }

    public void setExpires(Duration expires) {
        this.expires = expires;
    }

    public int getUserDayNum() {
        return userDayNum;
    }

    public void setUserDayNum(int userDayNum) {
        this.userDayNum = userDayNum;
    }

    public AliSms getAliSms() {
        return aliSms;
    }

    /**
     * 阿里云信配置信息
     */
    public static class AliSms {
        /**
         * 阿里云短信密钥
         */
        private String accessKeyId;

        /**
         * 阿里云短信密钥令牌
         */
        private String accessKeySecret;

        /**
         * 阿里云短信验证码签名
         */
        private String sign;

        /**
         * 短信通用模板
         */
        private String defaultTemplate;

        /**
         * 阿里云短信模板
         */
        private Map<String, String> templates = Maps.newHashMap();

        /**
         * 阿里云短信默认配置信息
         */
        private String product = "Dysmsapi";
        private String domain = "dysmsapi.aliyuncs.com";
        private String region = "cn-hangzhou";

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getDefaultTemplate() {
            return defaultTemplate;
        }

        public void setDefaultTemplate(String defaultTemplate) {
            this.defaultTemplate = defaultTemplate;
        }

        public Map<String, String> getTemplates() {
            return templates;
        }

        public void setTemplates(Map<String, String> templates) {
            this.templates = templates;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

    }
}
