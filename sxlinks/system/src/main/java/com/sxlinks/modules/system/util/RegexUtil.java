package com.sxlinks.modules.system.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 常用正则工具
 *
 * @author rickliu
 * @date 2019/6/6
 */
public final class RegexUtil {

    /**
     * 手机号正则表达式
     */
    private static final String PHONE_REGEX = "^(((13[0-9])|(14[0-9])|(15([0-9]))|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8})$";

    /**
     * 身份证号正则表达式
     */
    private static final String ID_CARD_REGEX = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X)$)";

    /**
     * 邮箱正则表达式
     */
    private static final String EMAIL_REGEX = "^\\w+@(\\w+\\.)+\\w+$";

    /**
     * 邮箱校验
     *
     * @param email 邮箱
     * @return true/false
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            return email.matches(EMAIL_REGEX);
        }
        return false;
    }

    /**
     * 手机号校验
     *
     * @param phone 手机号
     * @return true/false
     */
    public static boolean checkPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            return phone.matches(PHONE_REGEX);
        }
        return false;
    }

    /**
     * 身份证校验
     *
     * @param idCard 身份证号
     * @return true/false
     */
    public static boolean checkIdCard(String idCard) {
        return idCard.matches(ID_CARD_REGEX);
    }
}
