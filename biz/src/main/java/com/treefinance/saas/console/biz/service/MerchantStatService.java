package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.StatDayRequest;
import com.treefinance.saas.console.common.domain.request.StatRequest;
import com.treefinance.saas.console.common.domain.vo.MerchantStatOverviewTimeVO;
import com.treefinance.saas.knife.result.SaasResult;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/5.
 */
public interface MerchantStatService {

    SaasResult<Map<String, Object>> queryDayAccessList(StatRequest request);

    SaasResult<Map<String, Object>> queryWeekAccessList(StatRequest request);

    SaasResult<Map<String, Object>> queryMonthAccessList(StatRequest request);

    Map<String, Object> queryAllAccessList(StatRequest request);

    /**
     * 系统总任务量监控(饼状图)
     *
     * @param request
     * @return
     */
    Map<String, Object> queryAllAccessList4Pie(StatRequest request);

    Map<String, Object> queryAccessNumberList(StatRequest request);

    Map<String, Object> queryAccessRateList(StatRequest request);

    /**
     * 商户任务总览列表
     *
     * @param request
     * @return
     */
    List<MerchantStatOverviewTimeVO> queryOverviewAccessList(StatRequest request);

    /**
     * 商户任务总览失败率取消率详情
     *
     * @param request
     * @return
     */
    SaasResult<Map<String, Object>> queryOverviewDetailAccessList(StatDayRequest request);

    /**
     * 任务失败取消环节统计表
     *
     * @param request
     * @return
     */
    Map<String, Object> queryTaskStepStatInfo(StatRequest request);
}
