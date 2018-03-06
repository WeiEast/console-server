package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.OperatorStatRequest;

/**
 * Created by haojiahong on 2017/11/1.
 */
public interface OperatorStatService {

    Object queryAllOperatorStatDayAccessList(OperatorStatRequest request);

    Object queryAllOperatorStatAccessList(OperatorStatRequest request);

    /**
     * 查询某一个时刻下所有运营商任务情况
     *
     * @param request
     * @return
     */
    Object queryAllOperatorStatAccessSomeTimeList(OperatorStatRequest request);

    Object queryOperatorStatDayAccessList(OperatorStatRequest request);


    Object queryOperatorStatDayDetailAccessList(OperatorStatRequest request);

    /**
     * 获取有运营商权限的商户列表
     *
     * @return
     */
    Object queryMerchantsHasOperatorAuth();

    /**
     * 运营商任务占比监控
     *
     * @param request
     * @return
     */
    Object queryNumberRatio(OperatorStatRequest request);

    /**
     * 获取所有商户运营商每旬转化率的列表
     *
     * */
    Object queryAllOperatorStatConvertRateList(OperatorStatRequest request);


}
