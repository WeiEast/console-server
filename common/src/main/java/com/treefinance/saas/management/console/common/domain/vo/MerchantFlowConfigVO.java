package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/9/28.
 */
public class MerchantFlowConfigVO implements Serializable {
    private static final long serialVersionUID = 4399083039158113254L;

    private Long id;//主键id,批量更新使用
    private String appId;//商户id
    private String appName;
    private String serviceTag;//关联服务
    private String serviceTagName;//关联服务名称

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

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public String getServiceTagName() {
        return serviceTagName;
    }

    public void setServiceTagName(String serviceTagName) {
        this.serviceTagName = serviceTagName;
    }
}