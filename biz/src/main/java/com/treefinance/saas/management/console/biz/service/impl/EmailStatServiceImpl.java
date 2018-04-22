package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.management.console.biz.service.EmailStatService;
import com.treefinance.saas.management.console.common.domain.request.EmailStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.EmailStatAccessVO;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.monitor.facade.domain.request.EmailStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.email.EmailStatAccessBaseRO;
import com.treefinance.saas.monitor.facade.service.stat.EmailStatAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengtong
 * @date 18/3/15 15:26
 */
@Service
public class EmailStatServiceImpl implements EmailStatService {

    @Autowired
    EmailStatAccessFacade emailStatAccessFacade;

    @Override
    public Object queryEmailMonitorDayAccessList(EmailStatRequest request) {

        EmailStatAccessRequest rpcRequest = new EmailStatAccessRequest();

        rpcRequest.setStartTime(DateUtils.getTodayBeginDate(request.getStartDate()));
        rpcRequest.setEndTime(DateUtils.getTodayEndDate(request.getEndDate()));

        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());

        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setEmail(request.getEmail());
        MonitorResult<List<EmailStatAccessBaseRO>> rpcResult = emailStatAccessFacade.queryEmailStatDayAccessList(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, rpcResult.getData());
        }

        List<EmailStatAccessVO> result = BeanUtils.convertList(rpcResult.getData(), EmailStatAccessVO.class);
        result.stream().sorted();
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryEmailMonitorDayAccessListDetail(EmailStatRequest request) {


        EmailStatAccessRequest rpcRequest = new EmailStatAccessRequest();

        rpcRequest.setStartTime(DateUtils.getTodayBeginDate(request.getDataDate()));
        rpcRequest.setEndTime(DateUtils.getTodayEndDate(request.getDataDate()));

        rpcRequest.setPageSize(request.getPageSize());
        rpcRequest.setPageNumber(request.getPageNumber());

        rpcRequest.setStatType(request.getStatType());
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setEmail(request.getEmail());
        MonitorResult<List<EmailStatAccessBaseRO>> rpcResult = emailStatAccessFacade.queryEmailStatDayAccessListDetail(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, rpcResult.getData());
        }

        List<EmailStatAccessVO> result = BeanUtils.convertList(rpcResult.getData(), EmailStatAccessVO.class);


        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), result);

    }

    @Override
    public Object queryEmailSupportList(EmailStatRequest request) {
        MonitorResult<List<String>> rpcResult = emailStatAccessFacade
                .queryEmailSupportList(new EmailStatAccessRequest());
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newSuccessPageResult(request, 0, rpcResult.getData());
        }
        return Results.newSuccessPageResult(request, rpcResult.getTotalCount(), rpcResult.getData());
    }
}
