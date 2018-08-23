package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.QuestionnaireStatService;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppQuestionnaireStatisticsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:02
 */

@RestController
@RequestMapping("/saas/console/questionnaire")
public class QuestionnaireStatController {

    @Autowired
    private QuestionnaireStatService questionnaireStatService;


    @RequestMapping(value = "/stat/list", method = RequestMethod.POST)
    public Object queryAppQuestionnaireStatistics(@RequestBody QueryAppQuestionnaireStatisticsRequest request) {
        return questionnaireStatService.queryAppQuestionnaireStatistics(request);
    }

    @RequestMapping(value = "/stat/list/download", method = RequestMethod.GET)
    public void downloadAppQuestionnaireStatistics(@RequestParam(value = "appId", required = false) String appId,
                                                   @RequestParam(value = "bizType", required = false) Byte bizType,
                                                   @RequestParam(value = "step", required = false) String step,
                                                   @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
                                                   @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate,
                                                   HttpServletRequest request, HttpServletResponse response) {
        QueryAppQuestionnaireStatisticsRequest queryAppQuestionnaireStatisticsRequest = new QueryAppQuestionnaireStatisticsRequest();
        queryAppQuestionnaireStatisticsRequest.setAppId(appId);
        queryAppQuestionnaireStatisticsRequest.setBizType(bizType);
        queryAppQuestionnaireStatisticsRequest.setEndDate(endDate);
        queryAppQuestionnaireStatisticsRequest.setStartDate(startDate);
        queryAppQuestionnaireStatisticsRequest.setStep(step);
        questionnaireStatService.downloadAppQuestionnaireStatistics(queryAppQuestionnaireStatisticsRequest, request, response);
    }


}
