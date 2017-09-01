package com.treefinance.saas.management.console.biz.service.rawdata.impl;

import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by haojiahong on 2017/8/30.
 */
@Service
public class ProxyProviderServiceImpl implements ProxyProviderService {

    @Autowired
    private DiamondConfig diamondConfig;


    @Override
    public String queryUserList() {
        String url = diamondConfig.getDomainRawdataWiseproxy() + "wiseproxy/proxy/users";
        return HttpClientUtils.doGet(url);
    }

    @Override
    public String queryProxyCat(String user) {
        String url = diamondConfig.getDomainRawdataWiseproxy() + "wiseproxy/proxy/status/cat";
        Map<String, Object> params = Maps.newHashMap();
        params.put("user", user);
        return HttpClientUtils.doGet(url, params);
    }
}
