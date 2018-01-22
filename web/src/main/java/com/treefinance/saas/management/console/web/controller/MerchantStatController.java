package com.treefinance.saas.management.console.web.controller;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.MerchantStatService;
import com.treefinance.saas.management.console.common.domain.request.StatDayRequest;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.management.console.common.domain.vo.SourceTypeVO;
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

    @RequestMapping(value = "/stataccess/all/pie", method = {RequestMethod.GET}, produces = "application/json")
    public Result<Map<String, Object>> getAllPie(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryAllAccessList4Pie(request));
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

    @RequestMapping(value = "/stataccess/all/overview/detail", method = {RequestMethod.GET}, produces = "application/json")
    public Object getOverviewDetail(StatDayRequest request) {
        return merchantStatService.queryOverviewDetailAccessList(request);
    }

    @RequestMapping(value = "/stataccess/taskstep/fail", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, Object>> getTaskStepFailInfo(StatRequest request) {
        return Results.newSuccessResult(merchantStatService.queryTaskStepStatInfo(request));
    }

    @RequestMapping(value = "/source/list", method = RequestMethod.GET, produces = "application/json")
    public Result<Object> getSourceTypeList() {
        List<SourceTypeVO> list = Lists.newArrayList();
        SourceTypeVO sourceTypeVO1 = new SourceTypeVO();
        sourceTypeVO1.setId(0L);
        sourceTypeVO1.setName("所有来源");
        list.add(sourceTypeVO1);

        SourceTypeVO sourceTypeVO2 = new SourceTypeVO();
        sourceTypeVO2.setId(1L);
        sourceTypeVO2.setName("SDK");
        list.add(sourceTypeVO2);

        SourceTypeVO sourceTypeVO3 = new SourceTypeVO();
        sourceTypeVO3.setId(2L);
        sourceTypeVO3.setName("H5");
        list.add(sourceTypeVO3);
        return Results.newSuccessResult(list);
    }

}
