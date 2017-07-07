package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.management.console.common.result.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by haojiahong on 2017/7/5.
 */
public class StatRequest extends PageRequest {

    /**
     * 自选开始日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 自选结束日期 (yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 排序规则：1-成功率升序，2-成功率降序，4-访问次数升序，5-访问次数降序
     */
    private Integer collationType;

    /**
     * 统计类型：默认0-全部，1-总数，2-成功，3-失败，4-取消
     */
    private Integer statType;

    private String appId;

    /**
     * 数据类型：0-合计，1:银行，2：电商，3:邮箱，4:运营商
     */
    private Byte bizType;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCollationType() {
        return collationType;
    }

    public void setCollationType(Integer collationType) {
        this.collationType = collationType;
    }

    public Integer getStatType() {
        return statType;
    }

    public void setStatType(Integer statType) {
        this.statType = statType;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }
}
