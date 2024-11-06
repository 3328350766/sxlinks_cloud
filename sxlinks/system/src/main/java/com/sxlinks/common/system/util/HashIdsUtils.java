package com.sxlinks.common.system.util;

import org.apache.commons.lang3.StringUtils;
import org.hashids.Hashids;

/**
 * @author rickliu
 * @date 2019-07-17
 */
public final class HashIdsUtils {

    private static final String SECRET_KEY = "sAFetyeVALuaTE";

    private static final String SECRET_ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final Integer CIPHER_TEXT_MINLENGTH = 6;

    private static String encode(String plainText, HashIdsEnum hashIdsEnum) {
        Hashids hashids = new Hashids(SECRET_KEY + "-" + hashIdsEnum.name(), CIPHER_TEXT_MINLENGTH, SECRET_ALPHABET);
        return hashids.encodeHex(plainText);
    }

    private static String decode(String cipherText, HashIdsEnum hashIdsEnum) {
        Hashids hashids = new Hashids(SECRET_KEY + "-" + hashIdsEnum.name(), CIPHER_TEXT_MINLENGTH, SECRET_ALPHABET);
        return hashids.decodeHex(cipherText);
    }

    public static String userIdEncode(Integer userId) {
        return encode(userId.toString(), HashIdsEnum.UserId);
    }

    public static Integer userIdDecode(String userIdCode) {
        String decode = decode(userIdCode, HashIdsEnum.UserId);
        if (StringUtils.isBlank(decode)) {
            return null;
        }
        return Integer.parseInt(decode);
    }

    public static String userPhoneEncode(String phone) {
        return encode(phone, HashIdsEnum.UserPhone);
    }

    public static String userPhoneDecode(String phone) {
        String decode = decode(phone, HashIdsEnum.UserPhone);
        if (StringUtils.isBlank(decode)) {
            return null;
        }
        return decode;
    }

    private enum HashIdsEnum {
        /**
         * 用户ID
         */
        UserId,
        UserPhone,
        ;

        HashIdsEnum() {
        }


    }
}

