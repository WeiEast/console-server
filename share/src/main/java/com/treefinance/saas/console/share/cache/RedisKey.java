package com.treefinance.saas.console.share.cache;


import java.util.concurrent.TimeUnit;

/**
 * @author luoyihua
 * @date 2017年5月18日 上午11:36:18
 */

public final class RedisKey {

    private static final String PREFIX = "saas-console-server:";

    private static final String TOKEN = "TOKEN";
    private static final String COLON = ":";
    private static final String LIMIT = "LIMIT";
    private final static String COUNTER = "COUNTER";

    private static String link(Object... key) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < key.length - 1; i++) {
            sb.append(key[i].toString()).append(COLON);
        }
        sb.append(key[i].toString());
        return sb.toString();
    }

    private static String wrap(String key) {
        return PREFIX + key;
    }

    public static String getTokenKey(String keyword) {
        return wrap(link(TOKEN, keyword));
    }

    public static String getLimitCounterKey(String ip, long time, TimeUnit timeUnit) {
        return wrap(link(COUNTER, LIMIT, time, timeUnit, ip));
    }

}
