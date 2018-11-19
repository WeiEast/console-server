package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.merchant.facade.request.console.QueryAppQuestionnaireStatisticsRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; /**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:04
 */
public interface QuestionnaireStatService {
    Object queryAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest request);


    void downloadAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest queryAppQuestionnaireStatisticsRequest, HttpServletRequest request, HttpServletResponse response);
}
