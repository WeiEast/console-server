package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.OperatorStatService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

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
        if (request == null || request.getStartDate() == null || request.getEndDate() == null
                || request.getStatType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryAllOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/all/detail/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayDetailList(OperatorStatRequest request) {
        if (request == null || request.getDataDate() == null
                || request.getStatType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryAllOperatorStatAccessList(request);
    }

    @RequestMapping(value = "/each/day/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDayList(OperatorStatRequest request) {
        if (request.getDataDate() == null || request.getStatType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/each/detail/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDetailList(OperatorStatRequest request) {
        if (request.getStartDate() == null || request.getEndDate() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getGroupCode()) || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return operatorStatService.queryOperatorStatDayDetailAccessList(request);
    }

    /**
     * 获取有运营商权限的商户列表
     *
     * @return
     */
    @RequestMapping(value = "/merchant/list", method = RequestMethod.GET)
    public Object queryMerchantsHasOperatorAuth() {
        return operatorStatService.queryMerchantsHasOperatorAuth();
    }


    /**
     * 运营商任务监控占比
     */
    @RequestMapping(value = "num/ratio", method = RequestMethod.GET, produces = "application/json")
    public Object queryNumberRatio(OperatorStatRequest request) {
        OperatorStatRequest statRequest = new OperatorStatRequest();
        Date startTime = DateUtils.truncate(new Date(), Calendar.HOUR);
        Date endTime = DateUtils.addHours(startTime, 1);
        if (request == null) {
            statRequest.setStatType((byte) 0);
            statRequest.setStartTime(startTime);
            statRequest.setEndTime(endTime);
            statRequest.setAppId("virtual_total_stat_appId");
        } else {
            statRequest.setStatType(request.getStatType() == null ? (byte) 0 : request.getStatType());
            statRequest.setStartTime(request.getStartTime() == null ? startTime : request.getStartTime());
            statRequest.setEndTime(request.getEndTime() == null ? endTime : request.getEndTime());
            statRequest.setAppId(request.getAppId() == null ? "virtual_total_stat_appId" : request.getAppId());
        }
        return operatorStatService.queryNumberRatio(statRequest);

    }
}
