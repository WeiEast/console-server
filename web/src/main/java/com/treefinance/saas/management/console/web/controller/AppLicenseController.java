package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 授权管理Controller
 * Created by yh-treefinance on 2017/5/19.
 */
@RestController
@RequestMapping("/saas/console/applicense")
public class AppLicenseController {

    @Autowired
    private AppLicenseService appLicenseService;

    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    public SaasResult<Map<String, Object>> getKeyList(PageRequest request) {
        return appLicenseService.getAppLicenseList(request);
    }


}
