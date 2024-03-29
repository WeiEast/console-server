package com.treefinance.saas.console.biz.service.impl;

import com.treefinance.saas.console.biz.service.AppQuestionnaireService;
import com.treefinance.saas.console.biz.service.MerchantFlowConfigService;
import com.treefinance.saas.console.common.domain.request.AppQuestionnaireDetailRequest;
import com.treefinance.saas.console.common.domain.request.AppQuestionnaireRequest;
import com.treefinance.saas.console.common.domain.request.QueryQuestionnaireRequest;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.AddAppQuestDetailRequest;
import com.treefinance.saas.merchant.facade.request.console.AddAppQuestionnaireRequest;
import com.treefinance.saas.merchant.facade.request.console.GetQuestionnaireRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppQuestionnaireRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.result.grapsever.AppQuestionListRO;
import com.treefinance.saas.merchant.facade.result.grapsever.AppQuestionnaireRO;
import com.treefinance.saas.merchant.facade.service.AppQuestionnaireFacade;
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
public class AppQuestionnaireServiceImpl extends AbstractService implements AppQuestionnaireService {

    private static Logger logger = LoggerFactory.getLogger(MerchantFlowConfigService.class);

    @Autowired
    private AppQuestionnaireFacade appQuestionnaireFacade;

    @Override
    public SaasResult queryAppQuestionnaire(QueryQuestionnaireRequest request) {

        String appId = request.getAppId();

        QueryAppQuestionnaireRequest rpcRequest = new QueryAppQuestionnaireRequest();
        rpcRequest.setAppId(appId);
        rpcRequest.setPageNum(request.getPageNumber());
        rpcRequest.setPageSize(request.getPageSize());

        MerchantResult<List<AppQuestionListRO>> result;

        try {
            result = appQuestionnaireFacade.queryAppQuestionList(rpcRequest);
        } catch (Exception e) {
            logger.error("从merchant-center获取数据失败，错误", e);
            throw new BizException(e.getMessage());
        }

        if (!result.isSuccess()) {
            throw new BizException(result.getRetMsg());
        }

        logger.info("从merchant-center获取数据：{}", result);

        return Results.newPageResult(request, result.getTotalCount(), result.getData());
    }

    @Override
    public SaasResult addAppQuestionnaire(AppQuestionnaireRequest request) {
        AddAppQuestionnaireRequest rpcRequest = this.convert(request, AddAppQuestionnaireRequest.class);

        List<AppQuestionnaireDetailRequest> details = request.getDetails();

        List<AddAppQuestDetailRequest> detailRpc = this.convert(details, AddAppQuestDetailRequest.class);

        rpcRequest.setDetails(detailRpc);

        MerchantResult<Boolean> result;

        try {
            result = appQuestionnaireFacade.saveAppQuestionnaire(rpcRequest);
        } catch (Exception e) {
            logger.error("从merchant-center获取数据失败，错误", e);
            throw new BizException(e.getMessage());
        }

        if (!result.isSuccess()) {
            throw new BizException(result.getRetMsg());
        }

        logger.info("从merchant-center获取数据：{}", result);
        return Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult getAppQuestionnaire(AppQuestionnaireRequest request) {
        GetQuestionnaireRequest rpcRequest = new GetQuestionnaireRequest();

        rpcRequest.setId(request.getId());

        MerchantResult<AppQuestionnaireRO> result;

        try {
            result = appQuestionnaireFacade.getQuestionnaireById(rpcRequest);
        } catch (Exception e) {
            logger.error("从merchant-center获取数据失败，错误", e);
            throw new BizException(e.getMessage());
        }
        logger.info("从merchant-center获取数据：{}", result);

        if (!result.isSuccess()) {
            throw new BizException(result.getRetMsg());
        }

        return Results.newSuccessResult(result.getData());
    }

    @Override
    public SaasResult updateAppQuestionnaire(AppQuestionnaireRequest request) {
        AddAppQuestionnaireRequest rpcRequest = this.convert(request, AddAppQuestionnaireRequest.class);

        List<AppQuestionnaireDetailRequest> details = request.getDetails();

        List<AddAppQuestDetailRequest> detailRpc = this.convert(details, AddAppQuestDetailRequest.class);
        rpcRequest.setDetails(detailRpc);

        MerchantResult<Boolean> result;

        try {
            result = appQuestionnaireFacade.updateAppQuestionnaire(rpcRequest);
        } catch (Exception e) {
            logger.error("从merchant-center获取数据失败，错误", e);
            throw new BizException(e.getMessage());
        }

        if (!result.isSuccess()) {
            throw new BizException(result.getRetMsg());
        }

        logger.info("从merchant-center获取数据：{}", result);
        return Results.newSuccessResult(result.getData());
    }
}
