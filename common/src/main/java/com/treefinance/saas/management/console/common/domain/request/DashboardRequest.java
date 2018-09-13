package com.treefinance.saas.management.console.common.domain.request;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 18/9/13 11:05
 */
public class DashboardRequest implements Serializable {

    private Byte bizType;

    private Byte saasEnv;

    private String startTime;

    private String endTime;

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public Byte getSaasEnv() {
        return saasEnv;
    }

    public void setSaasEnv(Byte saasEnv) {
        this.saasEnv = saasEnv;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
