package com.treefinance.saas.management.console.biz.service;

import com.treefinance.saas.management.console.common.domain.request.StatRequest;
import com.treefinance.saas.management.console.common.domain.vo.ChartStatVO;

import java.util.List;
import java.util.Map;

/**
 * Created by haojiahong on 2017/7/10.
 */
public interface ApiStatService {

    /**
     * 接口实时访问监控
     *
     * @param request
     * @return
     */
    Map<String, List<ChartStatVO>> queryAllAccessList(StatRequest request);

    /**
     * 系统日访问量
     *
     * @param request
     * @return
     */
    Map<String, List<ChartStatVO>> queryDayAccessList(StatRequest request);

    /**
     * api实时访问统计
     *
     * @param request
     * @param type    1:统计访问量;2:统计相应时间;3:统计请求错误
     * @return
     */
    Map<String, List<ChartStatVO>> queryStatAccessList(StatRequest request, Integer type);

    Map<String, Object> queryStatAccessRank(StatRequest request);
}
