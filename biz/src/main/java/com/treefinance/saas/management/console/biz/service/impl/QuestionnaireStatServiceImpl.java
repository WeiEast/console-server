package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.QuestionnaireStatService;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppQuestionnaireStatisticsRequest;
import com.treefinance.saas.merchant.center.facade.result.console.AppQuestionnaireDetailStatisticsResult;
import com.treefinance.saas.merchant.center.facade.result.console.AppQuestionnaireStatisticsResult;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.service.AppQuestionnaireFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.treefinance.saas.knife.common.CommonStateCode.NO_RELATED_DATA;


/**
 * @author:guoguoyun
 * @date:Created in 2018/8/21下午2:04
 */
@Service
public class QuestionnaireStatServiceImpl implements QuestionnaireStatService{

    private static  final Logger logger = LoggerFactory.getLogger(QuestionnaireStatService.class);

    @Autowired
    private AppQuestionnaireFacade appQuestionnaireFacade;



    @Override
    public SaasResult<List> queryAppQuestionnaireStatistics(QueryAppQuestionnaireStatisticsRequest request) {


     MerchantResult<List<AppQuestionnaireStatisticsResult>> listMerchantResult =  appQuestionnaireFacade.queryAppQuestionnaireStatistics(request);
     MerchantResult<List<AppQuestionnaireDetailStatisticsResult>> listMerchantResult1 = appQuestionnaireFacade.queryAppQuestionnaireDetailStatistics(request);

        if(!("SUCCESS").equals(listMerchantResult.getRetMsg())||!("SUCCESS").equals(listMerchantResult1.getRetMsg()))
        {
            return Results.newFailedResult(NO_RELATED_DATA,(listMerchantResult.getRetMsg()+listMerchantResult1.getRetMsg()));
        }

        List<Object> list = new ArrayList<>();
        list.addAll(listMerchantResult.getData());
        list.addAll(listMerchantResult1.getData());

        return Results.newSuccessResult(list);


    }
}
