package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.DashboardRequest;
import com.treefinance.saas.knife.result.SaasResult;

/**
 * @author chengtong
 * @date 18/9/13 11:09
 */
public interface DashBoardService {

    SaasResult getDashboardView(DashboardRequest request);

}
