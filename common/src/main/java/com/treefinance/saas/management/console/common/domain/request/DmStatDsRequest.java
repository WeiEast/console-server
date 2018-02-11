package com.treefinance.saas.management.console.common.domain.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DmStatDsRequest {

    private String dataApiNameType;

    private String appId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;


    public String getDataApiNameType() {
        return dataApiNameType;
    }

    public void setDataApiNameType(String dataApiNameType) {
        this.dataApiNameType = dataApiNameType;
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
