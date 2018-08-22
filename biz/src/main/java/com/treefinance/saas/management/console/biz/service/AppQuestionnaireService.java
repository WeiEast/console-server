package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.management.console.common.domain.request.QueryQuestionnaireRequest;

/**
 * @author chengtong
 * @date 18/8/21 10:35
 */
public interface AppQuestionnaireService {

    SaasResult queryAppQuestionnaire(QueryQuestionnaireRequest request);

    SaasResult addAppQuestionnaire(AppQuestionnaireRequest request);

    SaasResult updateAppQuestionnaire(AppQuestionnaireRequest request);

}
