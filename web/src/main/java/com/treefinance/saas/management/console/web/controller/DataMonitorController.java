package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.DmStatService;
import com.treefinance.saas.management.console.common.domain.request.DmStatDsRequest;
import com.treefinance.saas.management.console.common.domain.request.DmStatTpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/saas/console/monitor/")
public class DataMonitorController {
    private static final Logger logger = LoggerFactory.getLogger(DataMonitorController.class);

    @Autowired
    private DmStatService dmStatService;


    @RequestMapping(value = "/stat/dsactual", method = {RequestMethod.GET})
    public Object dsactual(DmStatDsRequest request) {
        if (request == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (request.getDataApiNameType() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "接口类型不能为空");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        if (request.getStartDate().after(request.getEndDate())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "开始时间不能晚于结束时间");
        }
        if (toLocalDataTime(request.getStartDate()).plusDays(30).isBefore(toLocalDataTime(request.getEndDate()))) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间跨度不能超过30天");
        }
        return dmStatService.statDsActual(request);
    }

    @RequestMapping(value = "/stat/dsday", method = {RequestMethod.GET})
    public Object dsday(DmStatDsRequest request) {
        if (request == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (request.getDataApiNameType() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "接口类型不能为空");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        if (request.getStartDate().after(request.getEndDate())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "开始时间不能晚于结束时间");
        }
        if (toLocalDataTime(request.getStartDate()).plusYears(1).isBefore(toLocalDataTime(request.getEndDate()))) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间跨度不能超过1年");
        }
        return dmStatService.statDsDay(request);
    }

    @RequestMapping(value = "/stat/tpactual", method = {RequestMethod.GET})
    public Object tpactual(DmStatTpRequest request) {
        if (request == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (request.getTpApiName() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "接口类型不能为空");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        if (request.getStartDate().after(request.getEndDate())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "开始时间不能晚于结束时间");
        }
        if (toLocalDataTime(request.getStartDate()).plusDays(30).isBefore(toLocalDataTime(request.getEndDate()))) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间跨度不能超过30天");
        }

        return dmStatService.statTpActual(request);
    }

    @RequestMapping(value = "/stat/tpday", method = {RequestMethod.GET})
    public Object tpday(DmStatTpRequest request) {
        if (request == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        if (request.getTpApiName() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "接口类型不能为空");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间参数不能为空");
        }
        if (request.getStartDate().after(request.getEndDate())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "开始时间不能晚于结束时间");
        }
        if (toLocalDataTime(request.getStartDate()).plusYears(1).isBefore(toLocalDataTime(request.getEndDate()))) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK, "时间跨度不能超过1年");
        }
        return dmStatService.statTpDay(request);
    }


    private LocalDateTime toLocalDataTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
