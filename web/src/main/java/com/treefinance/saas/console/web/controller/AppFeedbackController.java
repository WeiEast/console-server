package com.treefinance.saas.console.web.controller;

import com.treefinance.saas.console.biz.service.AppFeedbackService;
import com.treefinance.saas.console.common.domain.request.AppFeedbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反馈意见
 *
 * @author haojiahong
 * @date 2018/8/29
 */
@RestController
@RequestMapping("/saas/console/feedback")
public class AppFeedbackController {


    @Autowired
    private AppFeedbackService appFeedbackService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object queryList(AppFeedbackRequest request) {
        if (request != null && request.getBizType() != null && request.getBizType() == 0) {
            request.setBizType(null);
        }
        return appFeedbackService.queryList(request);
    }

}
