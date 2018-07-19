package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppBizLicenseService;
import com.treefinance.saas.management.console.common.domain.request.AppBizLicenseRequest;
import com.treefinance.saas.management.console.common.domain.vo.AppBizLicenseVO;
import com.treefinance.saas.management.console.common.domain.vo.AppCrawlerConfigParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.treefinance.saas.merchant.center.facade.request.common.PageRequest;


import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/4.
 */
@RestController
@RequestMapping("/saas/console/bizlicense/")
public class AppBizLicenseController {

    @Autowired
    private AppBizLicenseService appBizLicenseService;

    @RequestMapping(value = "select", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<List<AppBizLicenseVO>> selectBizLicenseByAppIdBizType(@RequestBody AppBizLicenseRequest request) {

        List<AppBizLicenseVO> result = appBizLicenseService.selectBizLicenseByAppIdBizType(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/quota/select", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<List<AppBizLicenseVO>> selectQuotaByAppIdBizType(@RequestBody AppBizLicenseRequest request) {

        List<AppBizLicenseVO> result = appBizLicenseService.selectQuotaByAppIdBizType(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/traffic/select", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<List<AppBizLicenseVO>> selectTrafficByAppIdBizType(@RequestBody AppBizLicenseRequest request) {

        List<AppBizLicenseVO> result = appBizLicenseService.selectTrafficByAppIdBizType(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "update", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Boolean> updateAppBizLicense(@RequestBody AppBizLicenseVO request) {
        Boolean result = appBizLicenseService.updateAppBizLicense(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/quota/update", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Boolean> updateQuota(@RequestBody AppBizLicenseVO request) {
        Boolean result = appBizLicenseService.updateQuota(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/traffic/update", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Boolean> updateTraffic(@RequestBody AppBizLicenseVO request) {
        Boolean result = appBizLicenseService.updateTraffic(request);
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "/app/getList", produces = "application/json", method = RequestMethod.POST)
    public SaasResult<Map<String, Object>> selectListWithPaging(@RequestBody PageRequest request) {
        return appBizLicenseService.selectBizLicenseWithpaging(request);

    }


}
