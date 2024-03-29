package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商户基本信息
 * Created by haojiahong on 2017/6/21.
 */
public class MerchantBaseVO implements Serializable {

    private static final long serialVersionUID = 4923879171959418449L;
    private Long id;
    private String appId;
    private String appName;
    private String contactPerson;
    private String contactValue;
    private Byte isActive;
    private String loginName;
    private String password;
    private String company;
    private String chName;
    private String bussiness;
    private String bussiness2;
    private Date lastUpdateTime;
    private Boolean isTest;//是否是测试账户.0:正式账户;1:测试账户
    private List<AppBizLicenseVO> appBizLicenseVOList;
    private AppLicenseVO appLicenseVO;

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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<AppBizLicenseVO> getAppBizLicenseVOList() {
        return appBizLicenseVOList;
    }

    public void setAppBizLicenseVOList(List<AppBizLicenseVO> appBizLicenseVOList) {
        this.appBizLicenseVOList = appBizLicenseVOList;
    }

    public AppLicenseVO getAppLicenseVO() {
        return appLicenseVO;
    }

    public void setAppLicenseVO(AppLicenseVO appLicenseVO) {
        this.appLicenseVO = appLicenseVO;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getBussiness2() {
        return bussiness2;
    }

    public void setBussiness2(String bussiness2) {
        this.bussiness2 = bussiness2;
    }

    public Boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }


    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }
    @Override
    public String toString() {
        return "MerchantBaseVO{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", appName='" + appName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactValue='" + contactValue + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", company='" + company + '\'' +
                ", chName='" + chName + '\'' +
                ", bussiness='" + bussiness + '\'' +
                ", bussiness2='" + bussiness2 + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", isTest=" + isTest +
                ", appBizLicenseVOList=" + appBizLicenseVOList +
                ", appLicenseVO=" + appLicenseVO +
                '}';
    }
}
