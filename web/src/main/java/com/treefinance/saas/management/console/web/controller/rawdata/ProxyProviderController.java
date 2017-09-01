package com.treefinance.saas.management.console.web.controller.rawdata;

import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haojiahong on 2017/8/30.
 */
@RestController
@RequestMapping("/saas/console/rawdata/proxy")
public class ProxyProviderController {

    @Autowired
    private ProxyProviderService proxyProviderService;


    @RequestMapping(value = "/user/list", method = {RequestMethod.GET}, produces = "application/json")
    public String queryUserList() {
        return proxyProviderService.queryUserList();
    }

    @RequestMapping(value = "/cat", method = {RequestMethod.GET}, produces = "application/json")
    public String queryAll(String user) {
        return proxyProviderService.queryProxyCat(user);
    }

}
