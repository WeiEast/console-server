package com.treefinance.saas.management.console.web.controller.rawdata;

import com.treefinance.saas.management.console.biz.service.rawdata.ProxyProviderService;
import com.treefinance.saas.management.console.common.domain.vo.rawdata.ProxyCatVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haojiahong on 2017/8/30.
 */
@RestController
@RequestMapping("/saas/console/rawdata/proxy")
public class ProxyProviderController {

    @Autowired
    private ProxyProviderService proxyProviderService;

    @RequestMapping(value = "/user/list", method = {RequestMethod.GET}, produces = "application/json")
    public Result<List<String>> queryUserList() {
        return Results.newSuccessResult(proxyProviderService.queryUserList());
    }

    @RequestMapping(value = "/cat", method = {RequestMethod.GET}, produces = "application/json")
    public Result<ProxyCatVO> queryAll(String user) {
        return Results.newSuccessResult(proxyProviderService.queryProxyCat(user));
    }

}
