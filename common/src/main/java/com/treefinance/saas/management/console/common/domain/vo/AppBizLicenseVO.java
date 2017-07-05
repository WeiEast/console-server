package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/6/21.
 */
public class AppBizLicenseVO implements Serializable {

    private static final long serialVersionUID = -8509335907100898390L;
    private String appId;
    private Byte bizType;
    private String bizName;
    private Integer dailyLimit;
    private Byte isShowLicense;
    private Byte isValid;


    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Byte getIsShowLicense() {
        return isShowLicense;
    }

    public void setIsShowLicense(Byte isShowLicense) {
        this.isShowLicense = isShowLicense;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }
}
