package com.treefinance.saas.console.common.domain.request;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/4.
 */
public class AppBizLicenseRequest implements Serializable {

    private static final long serialVersionUID = 5387824470684922913L;

    private String appId;
    private Byte bizType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }
}
