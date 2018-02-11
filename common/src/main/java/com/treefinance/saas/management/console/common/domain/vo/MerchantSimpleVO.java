package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/4.
 */
public class MerchantSimpleVO implements Serializable {

    private static final long serialVersionUID = 2036307319563901821L;

    private Long id;

    private String appId;

    private String appName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public MerchantSimpleVO() {
    }

    public MerchantSimpleVO(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }
}
