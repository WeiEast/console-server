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
        if (request == null || request.getStartDate() == null || request.getEndDate() == null
                || request.getStatType() == null || StringUtils.isBlank(request.getAppId()) || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数startDate,endDate,statType,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryAllOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/all/detail/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayDetailList(OperatorStatRequest request) {
        if (request == null || request.getDataDate() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getAppId()) || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数dataDate,statType,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryAllOperatorStatAccessList(request);
    }

    @RequestMapping(value = "/all/detail/some/time/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllDayDetailSomeTimeList(OperatorStatRequest request) {
        if (request == null || request.getDataTime() == null || request.getSaasEnv() == null
                || request.getStatType() == null || StringUtils.isBlank(request.getAppId())) {
            throw new IllegalArgumentException("请求参数dataTime,saasEnv,statType,appId不能为空！");
        }
        return operatorStatService.queryAllOperatorStatAccessSomeTimeList(request);
    }


    @RequestMapping(value = "/each/day/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDayList(OperatorStatRequest request) {
        if (request.getDataDate() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getAppId()) || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数dataDate,statType,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryOperatorStatDayAccessList(request);
    }

    @RequestMapping(value = "/each/detail/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEachDetailList(OperatorStatRequest request) {
        if (request.getStartDate() == null || request.getEndDate() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getGroupCode()) || StringUtils.isBlank(request.getAppId())
                || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数startDate,endDate,statType,groupCode,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryOperatorStatDayDetailAccessList(request);
    }

    @RequestMapping(value = "/all/avg/convert/rate/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryAllConvertRateList(OperatorStatRequest request) {
        return operatorStatService.queryAllOperatorStatConvertRateList(request);
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
    @RequestMapping(value = "/num/ratio", method = RequestMethod.GET, produces = "application/json")
    public Object queryNumberRatio(OperatorStatRequest request) {
        if (request == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("请求参数不能为空");
        }
        long start = request.getStartTime().getTime();
        long end = request.getEndTime().getTime();
        long dif = 3 * 60 * 60 * 1000L;
        if (end - start > dif) {
            throw new IllegalArgumentException("选取的时间范围过大");
        }
        if (end <= start) {
            throw new IllegalArgumentException("开始时间需小于结束时间");
        }
        request.setStatType(request.getStatType() == null ? (byte) 0 : request.getStatType());
        request.setAppId(request.getAppId() == null ? "virtual_total_stat_appId" : request.getAppId());
        request.setIntervalMins(request.getIntervalMins() == null ? 5 : request.getIntervalMins());

        return operatorStatService.queryNumberRatio(request);
    }

    /**
     * 添加环境标签后,需要初始化预警历史数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/init/alarm/history/data", method = RequestMethod.GET, produces = "application/json")
    public Object initAlarmHistoryData(OperatorStatRequest request) {
        if (request == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("请求参数startDate,endDate不能为空");
        }

        return operatorStatService.initAlarmHistoryData(request);
    }

    @RequestMapping(value = "/query/callback/failure/reason", method = RequestMethod.GET, produces = "application/json")
    public Object queryCallbackFailureReason(OperatorStatRequest request) {
        if (request == null || request.getDataTime() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getAppId()) || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数dataTime,statType,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryCallbackFailureReason(request);
    }


    @RequestMapping(value = "/query/day/callback/failure/reason", method = RequestMethod.GET, produces = "application/json")
    public Object queryDayCallbackFailureReason(OperatorStatRequest request) {
        if (request == null || request.getDataDate() == null || request.getStatType() == null
                || StringUtils.isBlank(request.getAppId()) || request.getSaasEnv() == null) {
            throw new IllegalArgumentException("请求参数dataDate,statType,appId,saasEnv不能为空！");
        }
        return operatorStatService.queryDayCallbackFailureReason(request);
    }


}
