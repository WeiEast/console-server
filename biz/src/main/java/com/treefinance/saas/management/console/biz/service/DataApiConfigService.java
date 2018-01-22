package com.treefinance.saas.management.console.biz.service;

import java.util.Map;

public interface DataApiConfigService {

    /**
     * 获取DataServer层distinct type的类型
     * @return  key: [dataApiName]-[type]  blacklist-1
     *          val:desc    华道黑名单接口
     */
    Map<String, String> getDsApiConfigType();

}
