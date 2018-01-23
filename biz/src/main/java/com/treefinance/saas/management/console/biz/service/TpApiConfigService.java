package com.treefinance.saas.management.console.biz.service;

import java.util.Map;

public interface TpApiConfigService {

    /**
     * 获取TP层distinct type的类型
     * @return key:TpApiName   blk_hd
     *          val:desc        华道黑名单接口
     */
    Map<String, String> getTpApiConfigType();
}
