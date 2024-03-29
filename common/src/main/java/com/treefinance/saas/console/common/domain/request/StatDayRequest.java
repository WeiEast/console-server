package com.treefinance.saas.console.common.domain.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by haojiahong on 2017/8/21.
 */
public class StatDayRequest extends StatRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 来源(查询条件)
     */
    private String websiteDetailName;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getWebsiteDetailName() {
        return websiteDetailName;
    }

    public void setWebsiteDetailName(String websiteDetailName) {
        this.websiteDetailName = websiteDetailName;
    }
}
