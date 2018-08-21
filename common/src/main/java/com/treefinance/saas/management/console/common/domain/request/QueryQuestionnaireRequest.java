package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;

/**
 * @author chengtong
 * @date 18/8/21 10:29
 */
public class QueryQuestionnaireRequest extends PageRequest {

    private String appId;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
