package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.vo.TrafficLimitConfigVO;

import java.util.List;

/**
 * 流量管理
 * Created by haojiahong on 2017/7/25.
 */
public interface ConsoleTrafficLimitConfigService {

    /**
     * 列表
     *
     * @return
     */
    List<TrafficLimitConfigVO> getList();

    /**
     * 根据bizType更新
     *
     * @param trafficLimitConfigVO
     */
    void updateByBizType(TrafficLimitConfigVO trafficLimitConfigVO);
}
