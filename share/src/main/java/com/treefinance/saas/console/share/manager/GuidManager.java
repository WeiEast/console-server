package com.treefinance.saas.console.share.manager;

/**
 * @author Jerry
 * @date 2018/11/24 22:32
 */
public interface GuidManager {

    long generateUniqueId();

    long[] generateUniqueIds(int number);
}
