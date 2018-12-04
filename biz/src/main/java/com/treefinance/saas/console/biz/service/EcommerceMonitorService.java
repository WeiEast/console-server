package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.OperatorStatRequest;

/**
 * @author:guoguoyun
 * @date:Created in 2018/1/15下午2:45
 */
public interface EcommerceMonitorService {

    Object queryAllEcommerceMonitorList(OperatorStatRequest request);

    Object queryDivisionEcommerceMonitorList(OperatorStatRequest request);

    Object queryAllEcommerceListByBizType(Integer bizType);
}
