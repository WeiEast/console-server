package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.biz.service.AppFeedbackService;
import com.treefinance.saas.management.console.common.domain.request.AppFeedbackRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AppFeedbackController.class);

    @Autowired
    private AppFeedbackService appFeedbackService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object queryList(AppFeedbackRequest request) {
        return appFeedbackService.queryList(request);
    }

}
