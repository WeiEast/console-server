package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.RealTimeStatService;
import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Good Luck Bro , No Bug !
 * 任务实时监控功能
 *
 * @author haojiahong
 * @date 2018/6/21
 */
@RestController
@RequestMapping("/saas/console/real/time/stat")
public class RealTimeStatController {

    private static final Logger logger = LoggerFactory.getLogger(RealTimeStatController.class);

    @Autowired
    private RealTimeStatService realTimeStatService;

    @RequestMapping(value = "/number", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<Map<String, Object>> getNumber(StatRequest request) {
        logger.info("输入参数:request={}", JSON.toJSONString(request));
        if (request == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        if (request.getBizType() == null) {
            throw new IllegalArgumentException("请求参数bizType不能为空！");
        }
        if (request.getDateType() == null || request.getDateType() < 0 || request.getDateType() > 2) {
            throw new IllegalArgumentException("请求参数dateType为空或非法!");
        }
        if (request.getDateType() == 0) {
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("请求参数startDate或endDate不能为空！");
            }
            if (request.getStartDate().after(request.getEndDate())) {
                throw new IllegalArgumentException("请求参数startDate不能晚于endDate！");
            }
        }
        return Results.newSuccessResult(realTimeStatService.queryAccessNumberList(request));
    }

    @RequestMapping(value = "/rate", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<Map<String, Object>> getRate(StatRequest request) {
        logger.info("输入参数:request={}", JSON.toJSONString(request));
        if (request == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        if (request.getBizType() == null) {
            throw new IllegalArgumentException("请求参数bizType不能为空！");
        }
        if (request.getDateType() == null || request.getDateType() < 0 || request.getDateType() > 2) {
            throw new IllegalArgumentException("请求参数dateType为空或非法!");
        }
        if (request.getDateType() == 0) {
            if (request.getStartDate() == null || request.getEndDate() == null) {
                throw new IllegalArgumentException("请求参数startDate或endDate不能为空！");
            }
            if (request.getStartDate().after(request.getEndDate())) {
                throw new IllegalArgumentException("请求参数startDate不能晚于endDate！");
            }
        }
        return Results.newSuccessResult(realTimeStatService.queryAccessRateList(request));
    }
}
