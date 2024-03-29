package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.OperatorStatRequest;

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
     * 获取所有商户运营商每月转化率的列表
     */
    Object queryAllOperatorStatConvertRateList(OperatorStatRequest request);


    /**
     * 添加环境标签后,初始化历史预警数据
     *
     * @param request
     * @return
     */
    Object initAlarmHistoryData(OperatorStatRequest request);

    /**
     * 查询回调失败具体原因(按日)
     *
     * @param request
     * @return
     */
    Object queryDayCallbackFailureReason(OperatorStatRequest request);

    /**
     * 查询回调失败具体原因(分时)
     *
     * @param request
     * @return
     */
    Object queryCallbackFailureReason(OperatorStatRequest request);
}
