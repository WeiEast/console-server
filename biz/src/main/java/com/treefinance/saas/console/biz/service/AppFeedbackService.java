package com.treefinance.saas.console.biz.service;

import com.treefinance.saas.console.common.domain.request.AppFeedbackRequest;

/**
 * @author Jerry
 * @date 2018/11/17 02:39
 */
public interface AppFeedbackService {

    Object queryList(AppFeedbackRequest request);
}
