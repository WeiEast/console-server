package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;

/**
 * Created by haojiahong on 2017/11/1.
 */
public interface OperatorStatService {

    Object queryAllOperatorStatDayAccessList(OperatorStatRequest request);

    Object queryOperatorStatDayAccessList(OperatorStatRequest request);

    Object queryOperatorStatDayDetailAccessList(OperatorStatRequest request);
}
