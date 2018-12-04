package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.console.common.domain.request.QueryQuestionnaireRequest;
import com.treefinance.saas.knife.result.SaasResult;

/**
 * @author chengtong
 * @date 18/8/21 10:35
 */
public interface AppQuestionnaireService {

    SaasResult queryAppQuestionnaire(QueryQuestionnaireRequest request);

    SaasResult addAppQuestionnaire(AppQuestionnaireRequest request);

    SaasResult getAppQuestionnaire(AppQuestionnaireRequest request);

    SaasResult updateAppQuestionnaire(AppQuestionnaireRequest request);

}
