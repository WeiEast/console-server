package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.ConsoleTrafficLimitConfigService;
import com.treefinance.saas.management.console.common.domain.vo.TrafficLimitConfigVO;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流量管理
 * Created by haojiahong on 2017/7/25.
 */
@RestController
@RequestMapping("/saas/console/traffic/limit")
public class TrafficLimitConfigController {

    @Autowired
    private ConsoleTrafficLimitConfigService consoleTrafficLimitConfigService;

    @RequestMapping(value = "list", produces = "application/json", method = RequestMethod.GET)
    public Result<List<TrafficLimitConfigVO>> getTrafficList() {
        List<TrafficLimitConfigVO> result = consoleTrafficLimitConfigService.getList();
        return Results.newSuccessResult(result);
    }

    @RequestMapping(value = "update", produces = "application/json")
    public Result<Boolean> updateTraffic(@RequestBody TrafficLimitConfigVO trafficLimitConfigVO) {
        consoleTrafficLimitConfigService.updateByBizType(trafficLimitConfigVO);
        return Results.newSuccessResult(true);
    }


}
