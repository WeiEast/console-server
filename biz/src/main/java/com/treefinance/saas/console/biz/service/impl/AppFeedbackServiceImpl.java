package com.treefinance.saas.console.biz.service.impl;

import com.treefinance.saas.console.biz.service.AppFeedbackService;
import com.treefinance.saas.console.common.domain.request.AppFeedbackRequest;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.merchant.facade.request.console.GetAppFeedbackRequest;
import com.treefinance.saas.merchant.facade.result.console.AppFeedbackResultRO;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppFeedbackFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haojiahong
 * @date 2018/8/29
 */
@Service
public class AppFeedbackServiceImpl extends AbstractService implements AppFeedbackService {

    @Autowired
    private AppFeedbackFacade appFeedbackFacade;

    @Override
    public Object queryList(AppFeedbackRequest request) {
        GetAppFeedbackRequest rpcRequest = this.convert(request, GetAppFeedbackRequest.class);
        MerchantResult<List<AppFeedbackResultRO>> rpcResult;
        try {
            rpcResult = appFeedbackFacade.getAppQuestionnaire(rpcRequest);
        } catch (Exception e) {
            throw new BizException("merchant-center系统异常");
        }
        if (!rpcResult.isSuccess()) {
            throw new BizException(rpcResult.getRetMsg());
        }
        return Results.newPageResult(request, rpcResult.getTotalCount(), rpcResult.getData());
    }


}
