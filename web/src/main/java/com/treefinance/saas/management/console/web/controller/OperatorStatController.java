package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.OperatorStatService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营商监控
 *
 * @author haojiahong
 * @date 2017/11/1
 */
@RestController
@RequestMapping("/saas/console/operator/stat")
public class OperatorStatController {

    @Autowired
    private OperatorStatService operatorStatService;

    @RequestMapping(value = "/all/day/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayList(OperatorStatRequest request) {
        if (request.getStartDate() == null || request.getEndDate() == null || request.getStatType() == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryAllOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/each/day/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDayList(OperatorStatRequest request) {
        if (request.getDataDate() == null || request.getStatType() == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/each/detail/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDetailList(OperatorStatRequest request) {
        if (request.getStartDate() == null || request.getEndDate() == null || request.getStatType() == null || StringUtils.isBlank(request.getGroupCode())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryOperatorStatDayDetailAccessList(request);
    }


}
