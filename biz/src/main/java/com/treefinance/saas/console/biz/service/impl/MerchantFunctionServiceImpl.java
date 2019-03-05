package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.treefinance.saas.console.biz.service.MerchantFunctionService;
import com.treefinance.saas.console.common.domain.vo.MerchantFunctionVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.exception.BizException;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.request.PageRequest;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.merchant.facade.request.console.MerchantFunctionRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantFunctionResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.MerchantFunctionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张琰佳
 * @since 2:24 PM 2019/3/5
 */
@Service
public class MerchantFunctionServiceImpl extends AbstractService implements MerchantFunctionService {

    @Autowired
    private MerchantFunctionFacade merchantFunctionFacade;

    @Override
    public SaasResult<Integer> insert(MerchantFunctionRequest request) {
        MerchantResult<Integer> result;
        try {
            result = merchantFunctionFacade.insert(request);
        } catch (RpcException e) {
            logger.error("埋点新增商户异常", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        if (!result.isSuccess()) {
            logger.error("新增商户埋点失败，errorMsg={}", result.getRetMsg());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(result.getData());

    }

    @Override
    public SaasResult<Integer> update(MerchantFunctionRequest request) {
        MerchantResult<Integer> result;
        try {
            result = merchantFunctionFacade.updateSelective(request);
        } catch (RpcException e) {
            logger.error("埋点更新商户异常", e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        if (!result.isSuccess()) {
            logger.error("更新商户埋点失败，errorMsg={}", result.getRetMsg());
            return Results.newFailedResult(CommonStateCode.FAILURE);
        }
        return Results.newSuccessResult(result.getData());
    }

    @Override
    public MerchantFunctionVO getMerchantFunctionByAppId(MerchantFunctionRequest request) {
        MerchantResult<MerchantFunctionResult> result;
        try {
            result = merchantFunctionFacade.getMerchantFunctionByAppId(request);
        } catch (RpcException e) {
            logger.error("埋点查询商户异常", e);
            throw new BizException("系统异常");
        }
        if (!result.isSuccess()) {
            logger.error("埋点查询商户失败 errMsg={}", result.getRetMsg());
            throw new BizException("系统异常");
        }
        return convert(result.getData(), MerchantFunctionVO.class);
    }

    @Override
    public SaasResult<Map<String, Object>> queryMerchantFunctionList(PageRequest request) {
        com.treefinance.saas.merchant.facade.request.common.PageRequest pageRequest = new com.treefinance.saas.merchant.facade.request.common.PageRequest();

        pageRequest.setPageNum(request.getPageNumber());
        pageRequest.setPageSize(request.getPageSize());
        MerchantResult<List<MerchantFunctionResult>> result;
        try {
            result = merchantFunctionFacade.queryMerchantFunctionList(pageRequest);
        } catch (RpcException e) {
            logger.error("获取埋点商户列表异常", e);
            throw new BizException("系统异常");
        }
        if (!result.isSuccess()) {
            logger.error("获取埋点商户类表失败 errMsg={}", result.getRetMsg());
            throw new BizException("系统异常");
        }
        List<MerchantFunctionVO> merchantFunctionVOS = convertList(result.getData(), MerchantFunctionVO.class);
        return Results.newPageResult(request, result.getTotalCount(), merchantFunctionVOS);

    }
}
