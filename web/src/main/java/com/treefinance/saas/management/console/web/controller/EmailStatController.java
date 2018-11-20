package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.EmailStatService;
import com.treefinance.saas.management.console.common.domain.request.EmailStatRequest;
import com.treefinance.toolkit.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengtong
 * @date 18/3/15 15:00
 */
@RestController
@RequestMapping("/saas/console/email")
public class EmailStatController {

    @Autowired
    private EmailStatService emailStatService;

    @RequestMapping(value = "/monitor/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEmailMonitorDayAccessList(EmailStatRequest request) {
        if (request == null || request.getStartDate() == null || request.getEndDate() == null
            || request.getStatType() == null || StringUtils.isBlank(request.getAppId())
            || Objects.isEmpty(request.getEndDate()) || request.getEmail() == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return emailStatService.queryEmailMonitorDayAccessList(request);
    }

    @RequestMapping(value = "/monitor/list/detail", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEmailMonitorDayAccessListDetail(EmailStatRequest request) {
        if (request == null || request.getDataDate() == null || request.getStatType() == null
            || StringUtils.isBlank(request.getAppId()) || request.getEmail() == null) {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return emailStatService.queryEmailMonitorDayAccessListDetail(request);
    }

    @RequestMapping(value = "/support/list", method = {RequestMethod.GET}, produces = "application/json")
    public Object queryEmailMonitorSupportList(EmailStatRequest request) {
        return emailStatService.queryEmailSupportList(request);
    }

}
