package com.treefinance.saas.management.console.web.controller;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.management.console.biz.service.CallbackMsgStatService;
import com.treefinance.saas.management.console.common.domain.request.CallbackMsgStatRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/3/15
 */
@RestController
@RequestMapping("/saas/console/callback/msg/stat/")
public class CallbackMsgStatController {

    private static final Logger logger = LoggerFactory.getLogger(CallbackMsgStatController.class);
    @Autowired
    private CallbackMsgStatService callbackMsgStatService;

    @RequestMapping(value = "/day/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayList(CallbackMsgStatRequest request) {
        if (request == null || request.getStartDate() == null || request.getEndDate() == null
                || request.getBizType() == null || request.getDataType() == null || StringUtils.isBlank(request.getAppId())) {
            logger.error("日回调统计数据查询,请求参数缺失request={}", JSON.toJSONString(request));
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return callbackMsgStatService.queryCallbackMsgStatDayAccessList(request);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayDetailList(CallbackMsgStatRequest request) {
        if (request == null || request.getStartTime() == null || request.getEndTime() == null || request.getIntervalMins() == null
                || request.getBizType() == null || request.getDataType() == null || StringUtils.isBlank(request.getAppId())) {
            logger.error("分时回调统计数据查询,请求参数缺失request={}", JSON.toJSONString(request));
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return callbackMsgStatService.queryCallbackMsgStatAccessList(request);
    }
}
