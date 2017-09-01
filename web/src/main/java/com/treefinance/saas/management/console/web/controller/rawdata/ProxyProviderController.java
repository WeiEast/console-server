package com.treefinance.saas.management.console.web.controller.rawdata;

import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haojiahong on 2017/8/30.
 */
@RestController
@RequestMapping("/saas/console/rawdata/wiseproxy/internal/")
public class ProxyProviderController {

    @Autowired
    private ProxyProviderService proxyProviderService;


    @RequestMapping(value = "proxy/users", method = {RequestMethod.GET}, produces = "application/json")
    public Result<String> queryUserList() {
        return Results.newSuccessResult(proxyProviderService.queryUserList());
    }

    @RequestMapping(value = "proxy/status/cat", method = {RequestMethod.GET}, produces = "application/json")
    public Result<String> queryAll(String user) {
        return Results.newSuccessResult(proxyProviderService.queryProxyCat(user));
    }

}
