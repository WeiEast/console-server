package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by haojiahong on 2017/6/21.
 */
public class AppBizLicenseVO implements Serializable {

    private static final long serialVersionUID = -8509335907100898390L;
    private String appId;
    private Byte bizType;
    private String bizName;
    private Integer dailyLimit;
    private BigDecimal trafficLimit;
    private Byte isShowLicense;
    private Byte isValid;
    private String licenseTemplate;


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

    public BigDecimal getTrafficLimit() {
        return trafficLimit;
    }

    public void setTrafficLimit(BigDecimal trafficLimit) {
        this.trafficLimit = trafficLimit;
    }

    public String getLicenseTemplate() {
        return licenseTemplate;
    }

    public void setLicenseTemplate(String licenseTemplate) {
        this.licenseTemplate = licenseTemplate;
    }

    @Override
    public String toString() {
        return "AppBizLicenseVO{" +
                "appId='" + appId + '\'' +
                ", bizType=" + bizType +
                ", bizName='" + bizName + '\'' +
                ", dailyLimit=" + dailyLimit +
                ", trafficLimit=" + trafficLimit +
                ", isShowLicense=" + isShowLicense +
                ", isValid=" + isValid +
                ", licenseTemplate='" + licenseTemplate + '\'' +
                '}';
    }
}
