package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.Map;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:45
 */
public interface EcommerceMonitorService {

    Object queryAllEcommerceMonitorList(OperatorStatRequest request);

    Object queryDivisionEcommerceMonitorList(OperatorStatRequest request);

    Object queryAllEcommerceListByBizType(Integer bizType);
}
