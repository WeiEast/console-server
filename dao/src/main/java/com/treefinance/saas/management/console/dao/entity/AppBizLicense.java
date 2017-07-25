package com.treefinance.saas.management.console.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class AppBizLicense implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.Id
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.AppId
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private String appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.BizType
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Byte bizType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.isShowLicense
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Byte isShowLicense;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.isValid
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Byte isValid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.DailyLimit
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Integer dailyLimit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.CreateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column app_biz_license.LastUpdateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table app_biz_license
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.Id
     *
     * @return the value of app_biz_license.Id
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.Id
     *
     * @param id the value for app_biz_license.Id
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.AppId
     *
     * @return the value of app_biz_license.AppId
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.AppId
     *
     * @param appId the value for app_biz_license.AppId
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.BizType
     *
     * @return the value of app_biz_license.BizType
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Byte getBizType() {
        return bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.BizType
     *
     * @param bizType the value for app_biz_license.BizType
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.isShowLicense
     *
     * @return the value of app_biz_license.isShowLicense
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Byte getIsShowLicense() {
        return isShowLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.isShowLicense
     *
     * @param isShowLicense the value for app_biz_license.isShowLicense
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setIsShowLicense(Byte isShowLicense) {
        this.isShowLicense = isShowLicense;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.isValid
     *
     * @return the value of app_biz_license.isValid
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Byte getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.isValid
     *
     * @param isValid the value for app_biz_license.isValid
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.DailyLimit
     *
     * @return the value of app_biz_license.DailyLimit
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Integer getDailyLimit() {
        return dailyLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.DailyLimit
     *
     * @param dailyLimit the value for app_biz_license.DailyLimit
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.CreateTime
     *
     * @return the value of app_biz_license.CreateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.CreateTime
     *
     * @param createTime the value for app_biz_license.CreateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column app_biz_license.LastUpdateTime
     *
     * @return the value of app_biz_license.LastUpdateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column app_biz_license.LastUpdateTime
     *
     * @param lastUpdateTime the value for app_biz_license.LastUpdateTime
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_biz_license
     *
     * @mbg.generated Fri Jul 21 15:05:30 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appId=").append(appId);
        sb.append(", bizType=").append(bizType);
        sb.append(", isShowLicense=").append(isShowLicense);
        sb.append(", isValid=").append(isValid);
        sb.append(", dailyLimit=").append(dailyLimit);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}