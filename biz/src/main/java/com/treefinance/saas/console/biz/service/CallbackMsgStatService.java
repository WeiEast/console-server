package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.CallbackMsgStatRequest;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/3/15
 */
public interface CallbackMsgStatService {

    /**
     * 日回调数据统计查询
     *
     * @param request
     * @return
     */
    Object queryCallbackMsgStatDayAccessList(CallbackMsgStatRequest request);

    /**
     * 分时回调统计查询
     *
     * @param request
     * @return
     */
    Object queryCallbackMsgStatAccessList(CallbackMsgStatRequest request);

}
