package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.StatRequest;

import java.util.Map;

/**
 * Good Luck Bro , No Bug !
 * 任务实时监控
 *
 * @author haojiahong
 * @date 2018/6/25
 */
public interface RealTimeStatService {

    /**
     * 统计任务各个环节数量
     * 某时刻任务创建,登录成功,...,任务成功数量
     *
     * @param request
     * @return
     */
    Map<String, Object> queryAccessNumberList(StatRequest request);

    /**
     * 统计当前任务成功率以及前七天任务成功率均值
     * 某时刻任务成功率=某时刻任务成功数量/此时刻任务创建数量
     *
     * @param request
     * @return
     */
    Map<String, Object> queryAccessRateList(StatRequest request);
}
