package com.treefinance.saas.management.console.common.domain.vo;

/**
 * Created by haojiahong on 2017/7/6.
 */
public class MerchantStatSimpleVO extends ChartStatVO {

    private static final long serialVersionUID = -6609195218534565006L;

    private String appId;//appId

    private String appName;//appName

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
