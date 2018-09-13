package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.request.DashboardRequest;

/**
 * @author chengtong
 * @date 18/9/13 11:09
 */
public interface DashBoardService {

    SaasResult getDashboardView(DashboardRequest request);

}
