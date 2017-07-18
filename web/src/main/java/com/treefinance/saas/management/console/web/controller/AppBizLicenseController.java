package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by haojiahong on 2017/7/4.
 */
@RestController
@RequestMapping("/saas/console/bizlicense/")
public class AppBizLicenseController {

    @Autowired
    private AppBizLicenseService appBizLicenseService;

    @RequestMapping(value = "select", produces = "application/json", method = RequestMethod.POST)
    public Result<List<AppBizLicenseVO>> selectBizLicenseByAppIdBizType(@RequestBody AppBizLicenseRequest request) {

        List<AppBizLicenseVO> result = appBizLicenseService.selectBizLicenseByAppIdBizType(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/quota/select", produces = "application/json", method = RequestMethod.POST)
    public Result<List<AppBizLicenseVO>> selectQuotaByAppIdBizType(@RequestBody AppBizLicenseRequest request) {

        List<AppBizLicenseVO> result = appBizLicenseService.selectQuotaByAppIdBizType(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "update", produces = "application/json", method = RequestMethod.POST)
    public Result<Boolean> updateAppBizLicense(@RequestBody AppBizLicenseVO request) {
        Boolean result = appBizLicenseService.updateAppBizLicense(request);
        return Results.newSuccessResult(result);
    }


}
