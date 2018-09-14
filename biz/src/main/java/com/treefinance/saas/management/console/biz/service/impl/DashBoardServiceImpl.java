package com.treefinance.saas.management.console.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.biz.service.DashBoardService;
import com.treefinance.saas.management.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import com.treefinance.saas.monitor.facade.domain.request.DashboardStatRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.DashBoardResult;
import com.treefinance.saas.monitor.facade.service.stat.DashBoardFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengtong
 * @date 18/9/13 11:10
 */
@Service
public class DashBoardServiceImpl implements DashBoardService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DashBoardFacade dashBoardFacade;

    @Override
    public SaasResult getDashboardView(DashboardRequest request) {

        DashboardStatRequest statRequest = new DashboardStatRequest();

        statRequest.setBizType(request.getBizType());
        statRequest.setSaasEnv(request.getSaasEnv());

        MonitorResult<DashBoardResult> result = null;

        try{
            result = dashBoardFacade.queryDashboardResult(statRequest);
        }catch (Exception e){
            logger.info("请求monitor失败:{}", e.getMessage());
        }

        logger.info("从monitor获取的数据：{}", JSON.toJSONString(result));

        return Results.newSuccessResult(result.getData());
    }
}
