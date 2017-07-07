package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by haojiahong on 2017/7/5.
 */
public class MerchantStatVO implements Serializable {
    private static final long serialVersionUID = 1384575133068867069L;

    private String DateStr;//日期

    private Integer totalCount;//任务总数,使用量

    private Integer dailyLimit;//配额

    private BigDecimal dailyLimitRate;//配额使用率

    public String getDateStr() {
        return DateStr;
    }

    public void setDateStr(String dateStr) {
        DateStr = dateStr;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getDailyLimitRate() {
        return dailyLimitRate;
    }

    public void setDailyLimitRate(BigDecimal dailyLimitRate) {
        this.dailyLimitRate = dailyLimitRate;
    }
}
