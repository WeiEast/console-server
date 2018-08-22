package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.AppQuestionnaireService;
import com.treefinance.saas.management.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.management.console.common.domain.request.AppQuestionnaireDetailRequest;
import com.treefinance.saas.management.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.management.console.common.domain.request.QueryQuestionnaireRequest;
import com.treefinance.saas.management.console.common.utils.DataConverterUtils;
import com.treefinance.saas.merchant.center.facade.request.console.AddAppQuestDetailRequest;
import com.treefinance.saas.merchant.center.facade.request.console.AddAppQuestionnaireRequest;
import com.treefinance.saas.merchant.center.facade.request.console.QueryAppQuestionnaireRequest;
import com.treefinance.saas.merchant.center.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.center.facade.result.grapsever.AppQuestionListRO;
import com.treefinance.saas.merchant.center.facade.service.AppQuestionnaireFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengtong
 * @date 18/8/21 11:14
 */
@Service
public class AppQuestionnaireServiceImpl implements AppQuestionnaireService {

    private static Logger logger = LoggerFactory.getLogger(MerchantFlowConfigService.class);

    @Autowired
    private AppQuestionnaireFacade appQuestionnaireFacade;

    @Override
    public SaasResult queryAppQuestionnaire(QueryQuestionnaireRequest request) {

        String appId = request.getAppId();

        QueryAppQuestionnaireRequest rpcRequest = new QueryAppQuestionnaireRequest();
        rpcRequest.setAppId(appId);

        MerchantResult<List<AppQuestionListRO>> result;

        try {
            result = appQuestionnaireFacade.queryAppQuestionList(rpcRequest);
        } catch (Exception e) {
            logger.info("从merchant-center获取数据失败，错误：{}", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("从merchant-center获取数据：{}", result);

        return Results.newPageResult(request, result.getTotalCount(), result.getData());
    }

    @Override
    public SaasResult addAppQuestionnaire(AppQuestionnaireRequest request) {

        AddAppQuestionnaireRequest rpcRequest = DataConverterUtils.convert(request, AddAppQuestionnaireRequest.class);

        List<AppQuestionnaireDetailRequest> details = request.getDetails();

        List<AddAppQuestDetailRequest> detailRpc = DataConverterUtils.convert(details, AddAppQuestDetailRequest
                .class);

        rpcRequest.setDetails(detailRpc);

        MerchantResult<Boolean> result;

        try {
            result = appQuestionnaireFacade.saveAppQuestionnaire(rpcRequest);
        } catch (Exception e) {
            logger.info("从merchant-center获取数据失败，错误：{}", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("从merchant-center获取数据：{}", result);
        return Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult updateAppQuestionnaire(AppQuestionnaireRequest request) {
        AddAppQuestionnaireRequest rpcRequest = DataConverterUtils.convert(request, AddAppQuestionnaireRequest.class);

        List<AppQuestionnaireDetailRequest> details = request.getDetails();

        List<AddAppQuestDetailRequest> detailRpc = DataConverterUtils.convert(details, AddAppQuestDetailRequest
                .class);
        rpcRequest.setDetails(detailRpc);

        MerchantResult<Boolean> result;

        try {
            result = appQuestionnaireFacade.updateAppQuestionnaire(rpcRequest);
        } catch (Exception e) {
            logger.info("从merchant-center获取数据失败，错误：{}", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }

        logger.info("从merchant-center获取数据：{}", result);
        return Results.newSuccessResult(result.getData());
    }
}
