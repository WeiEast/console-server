package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.service.DashBoardService;
import com.treefinance.saas.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.knife.result.SaasResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengtong
 * @date 18/9/13 11:01
 */
@RestController
@RequestMapping("/saas/console/")
public class DashboardController {

    @Autowired
    private DashBoardService dashBoardService;

    @RequestMapping(value = "dashboard", method = RequestMethod.POST)
    public SaasResult getDashboard(@RequestBody DashboardRequest request) {

        if(request.getBizType() == null || request.getSaasEnv() == null){
            throw new IllegalArgumentException("bizType或saasEnv不能为空");
        }

        return dashBoardService.getDashboardView(request);

    }

}
