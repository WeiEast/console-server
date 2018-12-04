package com.treefinance.saas.console.util;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jerry
 * @date 2018/11/28 14:15
 */
public final class SystemUtils {

    private static final Logger logger = LoggerFactory.getLogger(SystemUtils.class);
    private static final String DA_SHU = "dashu";

    private SystemUtils() {
    }

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
        return RandomStringUtils.randomAlphanumeric(16);
    }
}
