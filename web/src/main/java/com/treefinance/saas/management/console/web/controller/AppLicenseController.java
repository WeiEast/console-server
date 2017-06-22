package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.Result;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.domain.vo.AppLicenseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 授权管理Controller
 * Created by yh-treefinance on 2017/5/19.
 */
@RestController
@RequestMapping("/saas/backend/applicense")
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

    @RequestMapping(value = "list", produces = "application/json")
    public Result<List<AppLicenseVO>> getkeyList() {
        return new Result<>(appLicenseService.getAppLicenseList());
    }
}
