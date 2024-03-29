package com.treefinance.saas.console.biz.service.impl;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.common.collect.Lists;
import com.treefinance.saas.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.console.common.domain.vo.AllEcommerceStatDayAccessVO;
import com.treefinance.saas.console.common.domain.vo.AllOperatorStatAccessVO;
import com.treefinance.saas.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.dao.entity.MerchantBase;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.merchant.facade.request.console.QueryMerchantByBizTypeRequest;
import com.treefinance.saas.merchant.facade.result.console.MerchantBaseResult;
import com.treefinance.saas.merchant.facade.result.console.MerchantResult;
import com.treefinance.saas.merchant.facade.service.MerchantBaseInfoFacade;
import com.treefinance.saas.monitor.facade.domain.request.EcommerceDetailAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.ecommerce.EcommerceAllDetailRO;
import com.treefinance.saas.monitor.facade.service.stat.EcommerceStatDivisionAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoguoyun
 * @date 2018/1/15下午2:45
 */
@Service
public class EcommerceMonitorServiceImpl extends AbstractService implements EcommerceMonitorService {


    @Autowired
    private EcommerceStatDivisionAccessFacade ecommerceStatDivisionAccessFacade;

    @Autowired
    private MerchantBaseInfoFacade merchantBaseInfoFacade;


    @Override
    public Object queryDivisionEcommerceMonitorList(OperatorStatRequest request) {

        EcommerceDetailAccessRequest ecommerceDetailAccessRequest = new EcommerceDetailAccessRequest();
        ecommerceDetailAccessRequest.setAppId(request.getAppId());
        ecommerceDetailAccessRequest.setSourceType(request.getSourceType());
        ecommerceDetailAccessRequest.setDataDate(request.getDataDate());
        ecommerceDetailAccessRequest.setStatType(request.getStatType());
        ecommerceDetailAccessRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<List<EcommerceAllDetailRO>> monitorResult =
                ecommerceStatDivisionAccessFacade.queryEcommerceAllDetailAccessList(ecommerceDetailAccessRequest);

        List<AllOperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.info("电商分时监控返回的值为空");
            return Results.newSuccessResult(result);
        }
        result = this.convertList(monitorResult.getData(), AllOperatorStatAccessVO.class);
        logger.info("电商分时监控返回信息为:{}", result.toString());

        return Results.newSuccessResult(result);


    }

    @Override
    public Object queryAllEcommerceMonitorList(OperatorStatRequest request) {
        EcommerceDetailAccessRequest ecommerceDetailAccessRequest = new EcommerceDetailAccessRequest();
        ecommerceDetailAccessRequest.setAppId(request.getAppId());
        ecommerceDetailAccessRequest.setStartDate(request.getStartDate());
        ecommerceDetailAccessRequest.setSourceType(request.getSourceType());
        ecommerceDetailAccessRequest.setEndDate(request.getEndDate());
        ecommerceDetailAccessRequest.setPageNumber(request.getPageNumber());
        ecommerceDetailAccessRequest.setPageSize(request.getPageSize());
        ecommerceDetailAccessRequest.setStatType(request.getStatType());
        ecommerceDetailAccessRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<List<EcommerceAllDetailRO>> monitorResult =
                ecommerceStatDivisionAccessFacade.queryEcommerceAllAccessList(ecommerceDetailAccessRequest);

        List<AllEcommerceStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.info("电商整体监控返回的值为空");
            return Results.newPageResult(request, 0, result);
        }
        result = this.convertList(monitorResult.getData(), AllEcommerceStatDayAccessVO.class);
        logger.info("电商整体监控返回信息为:{}", result.toString());

        return Results.newPageResult(request, monitorResult.getTotalCount(), result);


    }

    @Override
    public Object queryAllEcommerceListByBizType(Integer bizType) {
        List<MerchantSimpleVO> merchantSimpleVOS = new ArrayList<>();
        MerchantSimpleVO merchantSimpleVO = new MerchantSimpleVO("virtual_total_stat_appId", "所有商户");
        merchantSimpleVOS.add(merchantSimpleVO);
        List<MerchantBase> merchantBaseList = queryAllEcommerceListByBizeType(bizType);

        List<MerchantSimpleVO> merchantSimpleVOSSecond = this.convertList(merchantBaseList, MerchantSimpleVO.class);
        merchantSimpleVOS.addAll(merchantSimpleVOSSecond);

        if (CollectionUtils.isEmpty(merchantBaseList)) {
            logger.warn("电商整体监控返回的值为空");
            return Results.newSuccessResult(merchantSimpleVOS);
        }


        return Results.newSuccessResult(merchantSimpleVOS);
    }

    private List<MerchantBase> queryAllEcommerceListByBizeType(Integer bizType) {

        QueryMerchantByBizTypeRequest request = new QueryMerchantByBizTypeRequest();
        request.setBizType(bizType);


        MerchantResult<List<MerchantBaseResult>> rpcResult;

        try {
            logger.info("请求商户中心，request：{}",request);
            rpcResult = merchantBaseInfoFacade.queryMerchantByBizType(request);
        }catch (RpcException e){
            logger.error("根据业务类型获取列表数据失败：{}",e.getMessage());
            return new ArrayList<>();
        }

        if(rpcResult.isSuccess()){
            return this.convert(rpcResult.getData(), MerchantBase.class);
        }
        logger.error("请求商户中心，根据业务类型获取商户列表失败，{}",rpcResult.getRetMsg());
        return new ArrayList<>();
    }

}
