package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.StatRequest;
import com.treefinance.saas.console.common.domain.vo.ApiStatAccessVO;

import java.util.Date;
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
    Map<String, Object> queryAllAccessList(StatRequest request);

    /**
     * 系统日访问量
     *
     * @param request
     * @return
     */
    Map<String, Object> queryDayAccessList(StatRequest request);

    /**
     * api实时访问统计
     *
     * @param request
     * @param type    1:统计访问量;2:统计相应时间;3:统计请求错误
     * @return
     */
    Map<String, Object> queryStatAccessList(StatRequest request, Integer type);

    /**
     * api实时访问统计
     *
     * @param request
     * @return
     */
    List<ApiStatAccessVO> queryStatAccess(StatRequest request);

    /**
     * api每日访问排名统计
     *
     * @return
     */
    Map<String, Object> queryStatAccessRank(Date date);
}
