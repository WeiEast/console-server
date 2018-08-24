package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppQuestionnaireService;
import com.treefinance.saas.management.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.management.console.common.domain.request.QueryQuestionnaireRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengtong
 * @date 18/8/21 10:01
 */
@RestController
@RequestMapping("/saas/console/merchant/questionnaire")
public class AppQuestionnaireController {

    @Autowired
    AppQuestionnaireService appQuestionnaireService;

    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = "application/json")
    public SaasResult queryAppQuestionnaire(@RequestBody  QueryQuestionnaireRequest request) {
        return appQuestionnaireService.queryAppQuestionnaire(request);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = "application/json")
    public SaasResult addAppQuestionnaire(@RequestBody AppQuestionnaireRequest request) {
        return appQuestionnaireService.addAppQuestionnaire(request);
    }

    @RequestMapping(value = "/get",method = RequestMethod.POST,produces = "application/json")
    public SaasResult getAppQuestionnaire(@RequestBody AppQuestionnaireRequest request) {
        if(request.getAppId() == null){
            throw new IllegalArgumentException("请求参数appId不能为空");
        }

        return appQuestionnaireService.addAppQuestionnaire(request);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = "application/json")
    public SaasResult updateAppQuestionnaire(@RequestBody AppQuestionnaireRequest request) {
        return appQuestionnaireService.updateAppQuestionnaire(request);
    }




}
