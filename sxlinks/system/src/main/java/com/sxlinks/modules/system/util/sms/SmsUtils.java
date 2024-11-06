package com.sxlinks.modules.system.util.sms;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.sxlinks.common.exception.Asserts;
import com.sxlinks.common.exception.MiniException;
import com.sxlinks.common.util.RedisUtil;
import com.sxlinks.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/24
 */
@Slf4j
public final class SmsUtils {
    private static final SmsProperties SMS_PROPERTIES = SpringContextUtils.getBean(SmsProperties.class);
    private static final boolean PROD_MODE = SMS_PROPERTIES.isProdMode();
    private static final int USER_DAY_NUM = SMS_PROPERTIES.getUserDayNum();
    private static final boolean CONSOLE_ENABLED = SMS_PROPERTIES.isConsoleEnabled();
    private static final Duration EXPIRES = SMS_PROPERTIES.getExpires();

    private static final SmsProperties.AliSms ALI_SMS = SMS_PROPERTIES.getAliSms();
    private static final String REGION = ALI_SMS.getRegion();
    private static final String PRODUCT = ALI_SMS.getProduct();
    private static final String DOMAIN = ALI_SMS.getDomain();
    private static final String ACCESS_KEY_ID = ALI_SMS.getAccessKeyId();
    private static final String ACCESS_KEY_SECRET = ALI_SMS.getAccessKeySecret();
    private static final String SIGN = ALI_SMS.getSign();
    private static final String DEFAULT_TEMPLATE = ALI_SMS.getDefaultTemplate();
    private static final Map<String, String> TEMPLATES = ALI_SMS.getTemplates();

    private static final String SMS_SUCCESS_STATUS = "OK";

    private final static String SMS_KEY = "SMS:";

    private static final RedisUtil REDIS_UTIL = SpringContextUtils.getBean(RedisUtil.class);

    /**
     * 发送短信验证码
     *
     * @param phone    手机号
     * @param code     验证码
     * @param codeType 模板类型
     */
    public static void sendSms(String phone, String code, String codeType) {
        Map.Entry<String, String> template = TEMPLATES.entrySet().stream()
                .filter(obj -> StringUtils.equalsIgnoreCase(codeType, obj.getKey())).findFirst().orElse(null);

        String templateCode = DEFAULT_TEMPLATE;
        if (template != null) {
            templateCode = template.getValue();
        }

        IClientProfile profile = DefaultProfile.getProfile(REGION, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint(REGION, PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setTemplateCode(templateCode);
        request.setSignName(SIGN);

        Map<String, String> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(code)) {
            params.put("code", code);
            request.setTemplateParam(JSON.toJSONString(params));
        }
        SendSmsResponse smsResponse;
        try {
            smsResponse = acsClient.getAcsResponse(request);
            if (SmsUtils.isConsoleEnabled()) {
                log.info("短信验证码发送:{}-{}-{}", codeType, phone, code);
                log.info("发送结果:{}", smsResponse.getMessage());
            }
            Asserts.isTrue(StringUtils.equalsIgnoreCase(smsResponse.getCode(), SMS_SUCCESS_STATUS), 500, smsResponse.getMessage());
        } catch (ClientException e) {
            throw new MiniException(500, "阿里云短信服务异常");
        }
    }

    /**
     * 校验是否为生产环境
     *
     * @return true/false
     */
    public static boolean isProdMode() {
        return PROD_MODE;
    }

    /**
     * 生成短信验证码缓存KEY
     *
     * @param codeType 验证码类型
     * @param phone    手机号
     * @return SMS_BINDING_1XX
     */
    public static String generateCacheSmsKey(String codeType, String phone) {
        return Joiner.on("_").skipNulls().join("SMS", codeType, phone);
    }

    static boolean isConsoleEnabled() {
        return CONSOLE_ENABLED;
    }

    /**
     * 验证码有效时长(秒)
     *
     * @return 验证码有效时长
     */
    public static long getExpires() {
        return EXPIRES.getSeconds();
    }

    public static void validUserDayNum(String phone) {
        String smsKey = SMS_KEY + DateUtil.formatDate(new Date());

        //用户每天发送短信限制数量
        Asserts.isTrue(USER_DAY_NUM > 0, 500, "同一手机号24h内最多获取10次验证码,您已达标,请明天再试");

        Object o = REDIS_UTIL.hGet(smsKey, phone);
        if (o != null) {
            int num = Integer.parseInt(o.toString());
            Asserts.isTrue(USER_DAY_NUM > num, 500, "同一手机号24h内最多获取10次验证码,您已达标,请明天再试");
        }

        Long incrAfterNum = REDIS_UTIL.hIncrBy(smsKey, phone, 1);
        Asserts.isTrue(USER_DAY_NUM >= incrAfterNum.intValue(), 500, "同一手机号24h内最多获取10次验证码,您已达标,请明天再试");

        long expire = REDIS_UTIL.getExpire(smsKey);
        if (expire < 0) {
            REDIS_UTIL.expire(smsKey, 60 * 60 * 24);
        }
    }

}
