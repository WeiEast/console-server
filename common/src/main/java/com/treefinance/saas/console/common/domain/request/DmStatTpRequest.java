package com.treefinance.saas.console.common.domain.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DmStatTpRequest {

    private String tpApiName;

    private String appId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public String getTpApiName() {
        return tpApiName;
    }

    public void setTpApiName(String tpApiName) {
        this.tpApiName = tpApiName;
    }

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
}
