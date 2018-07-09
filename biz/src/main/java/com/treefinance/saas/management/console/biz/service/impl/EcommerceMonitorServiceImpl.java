package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.AllEcommerceStatDayAccessVO;
import com.treefinance.saas.management.console.common.domain.vo.AllOperatorStatAccessVO;
import com.treefinance.saas.management.console.common.domain.vo.MerchantSimpleVO;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.dao.ecommerce.EcommerceMonitorDao;
import com.treefinance.saas.management.console.dao.entity.MerchantBase;
import com.treefinance.saas.monitor.facade.domain.request.EcommerceDetailAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.ecommerce.EcommerceAllDetailRO;
import com.treefinance.saas.monitor.facade.service.stat.EcommerceStatDivisionAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:45
 */
@Service
public class EcommerceMonitorServiceImpl implements EcommerceMonitorService {

    private static Logger logger = LoggerFactory.getLogger(EcommerceMonitorServiceImpl.class);


    @Autowired
    EcommerceStatDivisionAccessFacade ecommerceStatDivisionAccessFacade;

    @Autowired
    EcommerceMonitorDao ecommerceMonitorDao;


    @Override
    public Object queryDivisionEcommerceMonitorList(OperatorStatRequest request) {

        EcommerceDetailAccessRequest ecommerceDetailAccessRequest = new EcommerceDetailAccessRequest();
        ecommerceDetailAccessRequest.setAppId(request.getAppId());
        ecommerceDetailAccessRequest.setSourceType(request.getSourceType());
        ecommerceDetailAccessRequest.setDataDate(request.getDataDate());
        ecommerceDetailAccessRequest.setStatType(request.getStatType());
//        ecommerceDetailAccessRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<List<EcommerceAllDetailRO>> monitorResult =
                ecommerceStatDivisionAccessFacade.queryEcommerceAllDetailAccessList(ecommerceDetailAccessRequest);

        List<AllOperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.info("电商分时监控返回的值为空");
            return Results.newSuccessResult(result);
        }
        result = BeanUtils.convertList(monitorResult.getData(), AllOperatorStatAccessVO.class);
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
//        ecommerceDetailAccessRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<List<EcommerceAllDetailRO>> monitorResult =
                ecommerceStatDivisionAccessFacade.queryEcommerceAllAccessList(ecommerceDetailAccessRequest);

        List<AllEcommerceStatDayAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.info("电商整体监控返回的值为空");
            return Results.newPageResult(request, 0, result);
        }
        result = BeanUtils.convertList(monitorResult.getData(), AllEcommerceStatDayAccessVO.class);
        logger.info("电商整体监控返回信息为:{}", result.toString());

        return Results.newPageResult(request, monitorResult.getTotalCount(), result);


    }

    @Override
    public Object queryAllEcommerceListByBizType(Integer bizType) {
        List<MerchantSimpleVO> merchantSimpleVOS = new ArrayList<>();
        MerchantSimpleVO merchantSimpleVO = new MerchantSimpleVO("virtual_total_stat_appId", "所有商户");
        merchantSimpleVOS.add(merchantSimpleVO);
        List<MerchantBase> merchantBaseList = ecommerceMonitorDao.queryAllEcommerceListByBizeType(bizType);

        List<MerchantSimpleVO> merchantSimpleVOSSecond = BeanUtils.convertList(merchantBaseList, MerchantSimpleVO.class);
        merchantSimpleVOS.addAll(merchantSimpleVOSSecond);

        if (CollectionUtils.isEmpty(merchantBaseList)) {
            logger.info("电商整体监控返回的值为空");
            return Results.newSuccessResult(merchantSimpleVOS);
        }


        return Results.newSuccessResult(merchantSimpleVOS);
    }

}
