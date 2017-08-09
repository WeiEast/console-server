package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.management.console.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/5.
 */
public interface MerchantStatService {

    Result<Map<String, Object>> queryDayAccessList(StatRequest request);

    Result<Map<String, Object>> queryWeekAccessList(StatRequest request);

    Result<Map<String, Object>> queryMonthAccessList(StatRequest request);

    Map<String, Object> queryAllAccessList(StatRequest request);

    Map<String, Object> queryAccessNumberList(StatRequest request);

    Map<String, Object> queryAccessRateList(StatRequest request);

    List<MerchantStatOverviewTimeVO> queryOverviewAccessList(StatRequest request);

}
