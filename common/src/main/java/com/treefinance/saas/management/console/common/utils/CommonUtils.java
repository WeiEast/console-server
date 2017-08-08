package com.treefinance.saas.management.console.common.utils;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商户后台管理通用工具类
 * Created by haojiahong on 2017/6/21.
 */
public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static final String DA_SHU = "dashu";

    public static String generateAppId() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    public static String generateLoginName(String appName) {
        String result = DA_SHU;
        try {
            result = PinyinHelper.convertToPinyinString(appName, "", PinyinFormat.WITHOUT_TONE);
            if (result.length() > 15) {
                result = PinyinHelper.getShortPinyin(appName);
            }
        } catch (Exception ignoreException) {
            logger.error("生成商户登录名出错,{}", ignoreException);
        }
        return result;
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(2) + RandomStringUtils.randomNumeric(4);
    }

    public static String encodeBase64(String str) {
        String b = Base64.encodeBase64String(str.getBytes());
        return b;
    }

    public static String decodeBase64(String str) {
        byte[] b = Base64.decodeBase64(str.getBytes());
        return new String(b);

    }

}
