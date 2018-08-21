package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.QuestionnaireStatService;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppQuestionnaireStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:02
 */

@RestController
@RequestMapping("/saas/console/questionnaire")
public class QuestionnaireStatController {

    @Autowired
    private QuestionnaireStatService questionnaireStatService;


    @RequestMapping(value = "/stat/list",method = RequestMethod.POST)
    public  Object  queryAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest request){
        return questionnaireStatService.queryAppQuestionnaireStatistics(request);
    }


}
