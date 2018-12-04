package com.treefinance.saas.console.manager.dubbo;

import com.treefinance.saas.console.manager.BizTypeManager;
import com.treefinance.saas.console.manager.domain.BizTypeBO;
import com.treefinance.saas.console.manager.domain.IdentifiedBizTypeBO;
import com.treefinance.saas.console.share.internal.RpcActionEnum;
import com.treefinance.saas.merchant.facade.request.common.BaseRequest;
import com.treefinance.saas.merchant.facade.request.console.QueryAppBizTypeRequest;
import com.treefinance.saas.merchant.facade.result.console.AppBizTypeResult;
import com.treefinance.saas.merchant.facade.result.console.AppBizTypeSimpleResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.AppBizTypeFacade;
import com.treefinance.toolkit.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Map;

/**
 * @author Jerry
 * @date 2018/11/27 10:07
 */
@Service
public class BizTypeServiceAdapter extends AbstractMerchantServiceAdapter implements BizTypeManager {

    private final AppBizTypeFacade bizTypeFacade;

    @Autowired
    public BizTypeServiceAdapter(AppBizTypeFacade bizTypeFacade) {
        this.bizTypeFacade = bizTypeFacade;
    }

    @Override
    public List<BizTypeBO> listBizTypes() {
        MerchantResult<List<AppBizTypeResult>> result = bizTypeFacade.queryAllAppBizType(new BaseRequest());

        validateResponse(result, RpcActionEnum.QUERY_BIZ_TYPE_LIST);

        return convert(result.getData(), BizTypeBO.class);
    }

    @Override
    public List<Byte> listBizTypeValues() {
        MerchantResult<List<AppBizTypeResult>> result = bizTypeFacade.queryAllAppBizType(new BaseRequest());

        validateResponse(result, RpcActionEnum.QUERY_BIZ_TYPE_LIST);

        return transform(result.getData(), AppBizTypeResult::getBizType);
    }

    @Override
    public List<IdentifiedBizTypeBO> listIdentifiedBizTypes() {
        MerchantResult<List<AppBizTypeSimpleResult>> result = bizTypeFacade.queryAppBizTypeSimple(new BaseRequest());

        validateResponse(result, RpcActionEnum.QUERY_BIZ_TYPE_LIST_SIMPLE);

        return convert(result.getData(), IdentifiedBizTypeBO.class);
    }

    @Override
    public List<BizTypeBO> listBizTypesInValues(@Nonnull List<Byte> bizTypeList) {
        Preconditions.notEmpty("bizTypes", bizTypeList);

        QueryAppBizTypeRequest request = new QueryAppBizTypeRequest();
        request.setBizTypes(bizTypeList);

        MerchantResult<List<AppBizTypeResult>> result = bizTypeFacade.queryAppBizType(request);

        validateResponse(result, RpcActionEnum.QUERY_BIZ_TYPE_LIST_ASSIGNED, request);

        return convert(result.getData(), BizTypeBO.class);
    }

    @Override
    public Map<Byte, String> getBizTypeNameMapping() {
        MerchantResult<List<AppBizTypeSimpleResult>> result = bizTypeFacade.queryAppBizTypeSimple(new BaseRequest());

        validateResponse(result, RpcActionEnum.QUERY_BIZ_TYPE_LIST_SIMPLE);

        return transformToMap(result.getData(), AppBizTypeSimpleResult::getBizType, AppBizTypeSimpleResult::getBizName);
    }
}
