package com.treefinance.saas.management.console.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 商户后台管理通用工具类
 * Created by haojiahong on 2017/6/21.
 */
public class CommonUtils {

    public static String generateAppId() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

}
