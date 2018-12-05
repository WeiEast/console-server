package com.treefinance.saas.console.manager.dubbo;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.console.manager.MerchantStatManager;
import com.treefinance.saas.console.manager.domain.DailyErrorStepStatBO;
import com.treefinance.saas.console.manager.domain.MerchantAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatBO;
import com.treefinance.saas.console.manager.domain.MerchantDailyAccessStatResultSet;
import com.treefinance.saas.console.manager.query.DailyErrorStepStatQuery;
import com.treefinance.saas.console.manager.query.MerchantAccessStatQuery;
import com.treefinance.saas.console.manager.query.MerchantDailyAccessStatQuery;
import com.treefinance.saas.console.context.component.RpcActionEnum;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.MerchantStatDayAccessRequest;
import com.treefinance.saas.monitor.facade.domain.request.SaasErrorStepDayStatRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.MerchantStatDayAccessRO;
import com.treefinance.saas.monitor.facade.domain.ro.stat.SaasErrorStepDayStatRO;
import com.treefinance.saas.monitor.facade.service.stat.MerchantStatAccessFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/11/29 15:49
 */
@Service
public class MerchantStatServiceAdapter extends AbstractMonitorServiceAdapter implements MerchantStatManager {
    @Autowired
    private MerchantStatAccessFacade merchantStatAccessFacade;

    @Override
    public MerchantDailyAccessStatResultSet queryDailyAccessStatisticsResultSet(@Nonnull MerchantDailyAccessStatQuery query) {
        MerchantStatDayAccessRequest request = buildMerchantStatDayAccessRequest(query);

        MonitorResult<List<MerchantStatDayAccessRO>> result = merchantStatAccessFacade.queryDayAccessList(request);

        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.queryDayAccessList() : request={}, result={}", JSON.toJSONString(request), JSON.toJSONString(result));
        }

        validateResponse(result, RpcActionEnum.STATISTICS_MERCHANT_DAILY_ACCESS_RESULT_SET, request);

        List<MerchantDailyAccessStatBO> list = convert(result.getData(), MerchantDailyAccessStatBO.class);

        return new MerchantDailyAccessStatResultSet(list, result.getTotalCount());
    }

    @Nonnull
    private MerchantStatDayAccessRequest buildMerchantStatDayAccessRequest(@Nonnull MerchantDailyAccessStatQuery query) {
        MerchantStatDayAccessRequest request = new MerchantStatDayAccessRequest();
        request.setAppId(query.getAppId());
        request.setDataType(query.getDataType());
        request.setStartDate(query.getStartDate());
        request.setEndDate(query.getEndDate());
        request.setIntervalMins(query.getIntervalMinutes());
        request.setSaasEnv(query.getSaasEnv());
        Integer pageNumber = query.getPageNumber();
        if (pageNumber != null) {
            request.setPageNumber(pageNumber);
        }
        Integer pageSize = query.getPageSize();
        if (pageSize != null) {
            request.setPageSize(pageSize);
        }
        return request;
    }

    @Override
    public List<MerchantDailyAccessStatBO> queryDailyAccessStatisticsRecords(@Nonnull MerchantDailyAccessStatQuery query) {
        MerchantStatDayAccessRequest request = buildMerchantStatDayAccessRequest(query);

        MonitorResult<List<MerchantStatDayAccessRO>> result;
        if (StringUtils.isEmpty(request.getAppId())) {
            result = merchantStatAccessFacade.queryAllDayAccessListNoPage(request);
            if (logger.isDebugEnabled()) {
                logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : request={},result={}", JSON.toJSONString(request), JSON.toJSONString(result));
            }
        } else {
            result = merchantStatAccessFacade.queryDayAccessListNoPage(request);
            if (logger.isDebugEnabled()) {
                logger.debug("merchantStatAccessFacade.queryDayAccessListNoPage() : request={},result={}", JSON.toJSONString(request), JSON.toJSONString(result));
            }
        }

        validateResponse(result, RpcActionEnum.STATISTICS_MERCHANT_DAILY_ACCESS_RECORDS, request);

        return convert(result.getData(), MerchantDailyAccessStatBO.class);
    }

    @Override
    public List<MerchantAccessStatBO> queryAccessStatisticsRecords(@Nonnull MerchantAccessStatQuery query) {
        MerchantStatAccessRequest request = new MerchantStatAccessRequest();
        request.setAppId(query.getAppId());
        request.setDataType(query.getDataType());
        request.setStartDate(query.getStartDate());
        request.setEndDate(query.getEndDate());
        request.setIntervalMins(query.getIntervalMinutes());
        request.setSaasEnv(query.getSaasEnv());

        MonitorResult<List<MerchantStatAccessRO>> result;
        RpcActionEnum action;
        if (query.getSuccess() == null) {
            result = merchantStatAccessFacade.queryAllAccessList(request);
            if (logger.isDebugEnabled()) {
                logger.debug("merchantStatAccessFacade.queryAllAccessList() : request={},result={}", JSON.toJSONString(request), JSON.toJSONString(result));
            }
            action = RpcActionEnum.STATISTICS_MERCHANT_ACCESS_RECORDS;
        } else if (Boolean.TRUE.equals(query.getSuccess())) {
            result = merchantStatAccessFacade.queryAllSuccessAccessList(request);
            if (logger.isDebugEnabled()) {
                logger.debug("merchantStatAccessFacade.queryAllSuccessAccessList() : request={},result={}", JSON.toJSONString(request), JSON.toJSONString(result));
            }
            action = RpcActionEnum.STATISTICS_MERCHANT_ACCESS_SUCCESS_RECORDS;
        } else {
            throw new UnsupportedOperationException("No method support to query failure access records!");
        }

        validateResponse(result, action, request);

        return convert(result.getData(), MerchantAccessStatBO.class);
    }

    @Override
    public List<DailyErrorStepStatBO> queryDailyErrorStepStatisticsRecords(@Nonnull DailyErrorStepStatQuery query) {
        SaasErrorStepDayStatRequest request = new SaasErrorStepDayStatRequest();
        request.setDataType(query.getDataType());
        request.setStartDate(query.getStartDate());
        request.setEndDate(query.getEndDate());

        MonitorResult<List<SaasErrorStepDayStatRO>> result = merchantStatAccessFacade.querySaasErrorStepDayStatListNoPage(request);
        if (logger.isDebugEnabled()) {
            logger.debug("merchantStatAccessFacade.querySaasErrorDayStatListNoPage() : request={},result={}", JSON.toJSONString(request), JSON.toJSONString(result));
        }

        validateResponse(result, RpcActionEnum.STATISTICS_ERROR_STEP_DAILY_RECORDS, request);

        return convert(result.getData(), DailyErrorStepStatBO.class);
    }

}
