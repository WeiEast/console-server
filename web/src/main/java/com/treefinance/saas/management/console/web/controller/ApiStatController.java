package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.ApiStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ApiStatAccessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/10.
 */
@RestController
@RequestMapping("/saas/console/api/stat")
public class ApiStatController {

    @Resource
    private ApiStatService apiStatService;

    @RequestMapping(value = "/all", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<Map<String, Object>> queryAll(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryAllAccessList(request));
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> queryDay(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryDayAccessList(request));
    }

    @RequestMapping(value = "/stataccess/total", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> queryStatAccessTotal(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 1));
    }

    @RequestMapping(value = "/stataccess/responsetime", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> queryStatAccessResponseTime(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 2));
    }

    @RequestMapping(value = "/stataccess/error", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> queryStatAccessError(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccessList(request, 3));
    }

    @RequestMapping(value = "/stataccess", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<List<ApiStatAccessVO>> queryStatAccess(StatRequest request) {
        return Results.newSuccessResult(apiStatService.queryStatAccess(request));
    }

    @RequestMapping(value = "/stataccess/rank", method = RequestMethod.GET, produces = "application/json")
    public SaasResult<Map<String, Object>> queryStatAccessRank(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return Results.newSuccessResult(apiStatService.queryStatAccessRank(date));
    }


}
