package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.ApiStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/10.
 */
@RestController
@RequestMapping("/saas/console/api/stat")
public class ApiStatController {

    @Autowired
    private ApiStatService apiStatService;

    @RequestMapping(value = "/all", method = {RequestMethod.GET}, produces = "application/json")
    public Result<Map<String, List<ChartStatVO>>> queryAll(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryAllAccessList(request));
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, List<ChartStatVO>>> queryDay(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryDayAccessList(request));
    }

    @RequestMapping(value = "/stataccess/total", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, List<ChartStatVO>>> queryStatAccessTotal(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 1));
    }

    @RequestMapping(value = "/stataccess/responsetime", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, List<ChartStatVO>>> queryStatAccessResponseTime(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 2));
    }

    @RequestMapping(value = "/stataccess/error", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, List<ChartStatVO>>> queryStatAccessError(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 3));
    }

    @RequestMapping(value = "/stataccess/rank", method = RequestMethod.GET, produces = "application/json")
    public Result<Map<String, Object>> queryStatAccessRank(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessRank(request));
    }


}
