package com.treefinance.saas.management.console.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class MerchantFlowConfig implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_flow_config.Id
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_flow_config.appId
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private String appId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_flow_config.serviceTag
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private String serviceTag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_flow_config.CreateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column merchant_flow_config.LastUpdateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_flow_config.Id
     *
     * @return the value of merchant_flow_config.Id
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_flow_config.Id
     *
     * @param id the value for merchant_flow_config.Id
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_flow_config.appId
     *
     * @return the value of merchant_flow_config.appId
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public String getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_flow_config.appId
     *
     * @param appId the value for merchant_flow_config.appId
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_flow_config.serviceTag
     *
     * @return the value of merchant_flow_config.serviceTag
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public String getServiceTag() {
        return serviceTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_flow_config.serviceTag
     *
     * @param serviceTag the value for merchant_flow_config.serviceTag
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag == null ? null : serviceTag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_flow_config.CreateTime
     *
     * @return the value of merchant_flow_config.CreateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_flow_config.CreateTime
     *
     * @param createTime the value for merchant_flow_config.CreateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column merchant_flow_config.LastUpdateTime
     *
     * @return the value of merchant_flow_config.LastUpdateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column merchant_flow_config.LastUpdateTime
     *
     * @param lastUpdateTime the value for merchant_flow_config.LastUpdateTime
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table merchant_flow_config
     *
     * @mbg.generated Thu Sep 28 15:19:45 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appId=").append(appId);
        sb.append(", serviceTag=").append(serviceTag);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}