package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商户基本信息
 * Created by haojiahong on 2017/6/21.
 */
public class MerchantBaseVO implements Serializable {

    private Long id;
    private String appId;
    private String appName;
    private String contactPerson;
    private String contactValue;
    private String loginName;
    private String password;
    private String company;
    private Date lastUpdateTime;
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
}
