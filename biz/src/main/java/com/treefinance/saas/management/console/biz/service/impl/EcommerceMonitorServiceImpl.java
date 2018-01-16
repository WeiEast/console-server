package com.treefinance.saas.management.console.biz.service.impl;

import com.google.common.collect.Lists;
import com.treefinance.saas.management.console.biz.service.EcommerceMonitorService;
import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.management.console.common.domain.vo.AllOperatorStatAccessVO;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.BeanUtils;
import com.treefinance.saas.monitor.facade.domain.request.EcommerceDetailAccessRequest;
import com.treefinance.saas.monitor.facade.domain.result.MonitorResult;
import com.treefinance.saas.monitor.facade.domain.ro.stat.ecommerce.EcommerceAllDetailRO;
import com.treefinance.saas.monitor.facade.service.stat.EcommerceDetailAccessFacade;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:45
 */
@Service
public class EcommerceMonitorServiceImpl implements EcommerceMonitorService {

    private static Logger logger = LoggerFactory.getLogger(EcommerceMonitorServiceImpl.class);


    @Autowired
    EcommerceDetailAccessFacade ecommerceDetailAccessFacade;


    @Override
    public Object queryAllEcommerceMonitorList(OperatorStatRequest request) {

        EcommerceDetailAccessRequest ecommerceDetailAccessRequest = new EcommerceDetailAccessRequest();
        ecommerceDetailAccessRequest.setAppId(request.getAppId());
        ecommerceDetailAccessRequest.setDataDate(request.getDataDate());
        ecommerceDetailAccessRequest.setStatType(request.getStatType());

        MonitorResult<List<EcommerceAllDetailRO>> monitorResult =
                ecommerceDetailAccessFacade.queryEcommerceAllDetailAccessList(ecommerceDetailAccessRequest);

        List<AllOperatorStatAccessVO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(monitorResult.getData())) {
            logger.info("电商分时监控返回的值为空");
            return Results.newSuccessResult(result);
        }
        result = BeanUtils.convertList(monitorResult.getData(), AllOperatorStatAccessVO.class);
        for (AllOperatorStatAccessVO allOperatorStatAccessVO : result) {
            logger.info("电商分时监控返回的数据为{}", allOperatorStatAccessVO.toString());
        }
        return Results.newSuccessResult(result);


    }

}
