package com.treefinance.saas.management.console.biz.service.impl;

import com.treefinance.saas.management.console.biz.service.CallbackMsgStatService;
import com.treefinance.saas.management.console.common.domain.request.CallbackMsgStatRequest;
import com.treefinance.saas.monitor.facade.domain.request.CallbackMsgStatAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.callback.AsStatDayCallbackRO;
import com.treefinance.saas.monitor.facade.service.stat.CallbackMsgStatAccessFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/3/15
 */
@Service
public class CallbackMsgStatServiceImpl implements CallbackMsgStatService {

    @Autowired
    private CallbackMsgStatAccessFacade callbackMsgStatAccessFacade;

    @Override
    public Object queryCallbackMsgStatDayAccessList(CallbackMsgStatRequest request) {
        CallbackMsgStatAccessRequest rpcRequest = new CallbackMsgStatAccessRequest();
        rpcRequest.setAppId(request.getAppId());
        rpcRequest.setDataType(request.getDataType());
        rpcRequest.setBizType(request.getBizType());
        rpcRequest.setStartTime(request.getStartDate());
        rpcRequest.setEndTime(request.getEndDate());
        MonitorResult<List<AsStatDayCallbackRO>> rpcResult = callbackMsgStatAccessFacade.queryCallbackMsgStatDayAccessList(rpcRequest);
        List<AsStatDayCallbackRO> rpcList = rpcResult.getData();


        return null;
    }

    @Override
    public Object queryCallbackMsgStatAccessList(CallbackMsgStatRequest request) {
        return null;
    }
}
