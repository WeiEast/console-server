package com.treefinance.saas.console.biz.service.impl;

import com.treefinance.saas.console.biz.service.EmailStatService;
import com.treefinance.saas.console.common.domain.request.EmailStatRequest;
import com.treefinance.saas.console.common.domain.vo.EmailStatAccessVO;
import com.treefinance.saas.console.context.component.AbstractService;
import com.treefinance.saas.console.util.DateUtils;
import com.treefinance.saas.knife.result.Results;
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
public class EmailStatServiceImpl extends AbstractService implements EmailStatService {

    @Autowired
    private EmailStatAccessFacade emailStatAccessFacade;

    @Override
    public Object queryEmailMonitorDayAccessList(EmailStatRequest request) {

        EmailStatAccessRequest rpcRequest = new EmailStatAccessRequest();

        this.copyProperties(request, rpcRequest);
        rpcRequest.setStartTime(DateUtils.getTodayBeginDate(request.getStartDate()));
        rpcRequest.setEndTime(DateUtils.getTodayEndDate(request.getEndDate()));

        MonitorResult<List<EmailStatAccessBaseRO>> rpcResult =
            emailStatAccessFacade.queryEmailStatDayAccessList(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, rpcResult.getData());
        }

        List<EmailStatAccessVO> result = this.convertList(rpcResult.getData(), EmailStatAccessVO.class);
        return Results.newPageResult(request, rpcResult.getTotalCount(), result);
    }

    @Override
    public Object queryEmailMonitorDayAccessListDetail(EmailStatRequest request) {

        EmailStatAccessRequest rpcRequest = new EmailStatAccessRequest();

        this.copyProperties(request, rpcRequest);
        rpcRequest.setStartTime(DateUtils.getTodayBeginDate(request.getDataDate()));
        rpcRequest.setEndTime(DateUtils.getTodayEndDate(request.getDataDate()));

        MonitorResult<List<EmailStatAccessBaseRO>> rpcResult =
            emailStatAccessFacade.queryEmailStatDayAccessListDetail(rpcRequest);
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, rpcResult.getData());
        }

        List<EmailStatAccessVO> result = this.convertList(rpcResult.getData(), EmailStatAccessVO.class);

        return Results.newPageResult(request, rpcResult.getTotalCount(), result);

    }

    @Override
    public Object queryEmailSupportList(EmailStatRequest request) {
        MonitorResult<List<String>> rpcResult =
            emailStatAccessFacade.queryEmailSupportList(new EmailStatAccessRequest());
        if (CollectionUtils.isEmpty(rpcResult.getData())) {
            return Results.newPageResult(request, 0, rpcResult.getData());
        }
        return Results.newPageResult(request, rpcResult.getTotalCount(), rpcResult.getData());
    }
}
