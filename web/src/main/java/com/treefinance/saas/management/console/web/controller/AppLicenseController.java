package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.result.PageRequest;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
    private static final Logger logger = LoggerFactory.getLogger(AppLicenseController.class);

    @Autowired
    private AppLicenseService appLicenseService;

    @RequestMapping("/get/{appId}")
    public Result<AppLicenseDTO> getApplicenseByAppId(@PathVariable String appId) {
        return new Result<>(appLicenseService.selectOneByAppId(appId));
    }

    @RequestMapping("/generate")
    public Result<String> generate() {
        return appLicenseService.generateAppLicense();
    }

    @RequestMapping("/generateBy/{appId}")
    public Result<Integer> generate(@PathVariable String appId) {
        return appLicenseService.generateAppLicenseByAppId(appId);
    }

    @RequestMapping(value = "list", produces = "application/json", method = RequestMethod.GET)
    public Result<Map<String, Object>> getKeyList(PageRequest request) {
        return appLicenseService.getAppLicenseList(request);
    }

    @RequestMapping(value = "/history/secretkey/init")
    public Object initHistorySecretKey() {
        appLicenseService.initHistorySecretKey();
        return Results.newSuccessResult("商户历史密钥初始化成功!");
    }
}
