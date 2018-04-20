package com.treefinance.saas.management.console.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class MerchantUser implements Serializable {
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.MerchantId
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private Long merchantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.LoginName
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private String loginName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.Password
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.IsActive
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private Boolean isActive;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.IsTest
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private Boolean isTest;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.CreateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_user.LastUpdateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.Id
     *
     * @return the value of merchant_user.Id
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.Id
     *
     * @param id the value for merchant_user.Id
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.MerchantId
     *
     * @return the value of merchant_user.MerchantId
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Long getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.MerchantId
     *
     * @param merchantId the value for merchant_user.MerchantId
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.LoginName
     *
     * @return the value of merchant_user.LoginName
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.LoginName
     *
     * @param loginName the value for merchant_user.LoginName
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.Password
     *
     * @return the value of merchant_user.Password
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.Password
     *
     * @param password the value for merchant_user.Password
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.IsActive
     *
     * @return the value of merchant_user.IsActive
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.IsActive
     *
     * @param isActive the value for merchant_user.IsActive
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.IsTest
     *
     * @return the value of merchant_user.IsTest
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Boolean getIsTest() {
        return isTest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.IsTest
     *
     * @param isTest the value for merchant_user.IsTest
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.CreateTime
     *
     * @return the value of merchant_user.CreateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.CreateTime
     *
     * @param createTime the value for merchant_user.CreateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_user.LastUpdateTime
     *
     * @return the value of merchant_user.LastUpdateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_user.LastUpdateTime
     *
     * @param lastUpdateTime the value for merchant_user.LastUpdateTime
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_user
     *
     * @mbg.generated Thu Jul 27 11:13:09 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", loginName=").append(loginName);
        sb.append(", password=").append(password);
        sb.append(", isActive=").append(isActive);
        sb.append(", isTest=").append(isTest);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}