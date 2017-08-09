package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.MerchantStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/5.
 */
@RestController
@RequestMapping("/saas/console/merchant/stat")
public class MerchantStatController {
    @Autowired
    private MerchantStatService merchantStatService;

    @RequestMapping(value = "/stataccess/total/days", method = {RequestMethod.GET})
    public Object getDays(StatRequest request) {
        return merchantStatService.queryDayAccessList(request);
    }

    @RequestMapping(value = "/stataccess/total/weeks", method = {RequestMethod.GET})
    public Object getWeeks(StatRequest request) {
        return merchantStatService.queryWeekAccessList(request);
    }

    @RequestMapping(value = "/stataccess/total/months", method = {RequestMethod.GET})
    public Object getMonths(StatRequest request) {
        return merchantStatService.queryMonthAccessList(request);
    }

    @RequestMapping(value = "/stataccess/all", method = {RequestMethod.GET}, produces = "application/json")
    public Result<Map<String, Object>> getAll(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryAllAccessList(request));
    }

    @RequestMapping(value = "/stataccess/number", method = {RequestMethod.GET}, produces = "application/json")
    public Result<Map<String, Object>> getNumber(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryAccessNumberList(request));
    }

    @RequestMapping(value = "/stataccess/rate", method = {RequestMethod.GET}, produces = "application/json")
    public Result<Map<String, Object>> getRate(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryAccessRateList(request));
    }

    @RequestMapping(value = "/stataccess/all/overview", method = {RequestMethod.GET}, produces = "application/json")
    public Result<List<MerchantStatOverviewTimeVO>> getOverview(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryOverviewAccessList(request));
    }


}
