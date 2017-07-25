package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by haojiahong on 2017/7/25.
 */
public class TrafficLimitConfigVO implements Serializable {

    private static final long serialVersionUID = 8466457868669138347L;

    private Byte bizType;
    private String bizName;
    private BigDecimal rate;

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
