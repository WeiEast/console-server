package com.treefinance.saas.management.console.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsoleTrafficLimitConfig implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column console_traffic_limit_config.Id
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column console_traffic_limit_config.BizType
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private Byte bizType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column console_traffic_limit_config.Rate
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private BigDecimal rate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column console_traffic_limit_config.CreateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column console_traffic_limit_config.LastUpdateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column console_traffic_limit_config.Id
     *
     * @return the value of console_traffic_limit_config.Id
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column console_traffic_limit_config.Id
     *
     * @param id the value for console_traffic_limit_config.Id
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column console_traffic_limit_config.BizType
     *
     * @return the value of console_traffic_limit_config.BizType
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public Byte getBizType() {
        return bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column console_traffic_limit_config.BizType
     *
     * @param bizType the value for console_traffic_limit_config.BizType
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column console_traffic_limit_config.Rate
     *
     * @return the value of console_traffic_limit_config.Rate
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column console_traffic_limit_config.Rate
     *
     * @param rate the value for console_traffic_limit_config.Rate
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column console_traffic_limit_config.CreateTime
     *
     * @return the value of console_traffic_limit_config.CreateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column console_traffic_limit_config.CreateTime
     *
     * @param createTime the value for console_traffic_limit_config.CreateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column console_traffic_limit_config.LastUpdateTime
     *
     * @return the value of console_traffic_limit_config.LastUpdateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column console_traffic_limit_config.LastUpdateTime
     *
     * @param lastUpdateTime the value for console_traffic_limit_config.LastUpdateTime
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table console_traffic_limit_config
     *
     * @mbg.generated Tue Jul 25 19:51:06 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bizType=").append(bizType);
        sb.append(", rate=").append(rate);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}