package com.treefinance.saas.console.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/9.
 */
public class MerchantStatOverviewTimeVO implements Serializable {

    private static final long serialVersionUID = 7652484326151285712L;

    private String appId;

    private String appName;

    private String time1Val;

    private String time2Val;

    private String time3Val;

    private String time4Val;

    private String time5Val;

    private String time6Val;

    private String time7Val;

    @JsonIgnore
    private Date appCreateTime;
    @JsonIgnore
    private Boolean appIsTest;
    @JsonIgnore
    private Integer num;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTime1Val() {
        return time1Val;
    }

    public void setTime1Val(String time1Val) {
        this.time1Val = time1Val;
    }

    public String getTime2Val() {
        return time2Val;
    }

    public void setTime2Val(String time2Val) {
        this.time2Val = time2Val;
    }

    public String getTime3Val() {
        return time3Val;
    }

    public void setTime3Val(String time3Val) {
        this.time3Val = time3Val;
    }

    public String getTime4Val() {
        return time4Val;
    }

    public void setTime4Val(String time4Val) {
        this.time4Val = time4Val;
    }

    public String getTime5Val() {
        return time5Val;
    }

    public void setTime5Val(String time5Val) {
        this.time5Val = time5Val;
    }

    public String getTime6Val() {
        return time6Val;
    }

    public void setTime6Val(String time6Val) {
        this.time6Val = time6Val;
    }

    public String getTime7Val() {
        return time7Val;
    }

    public void setTime7Val(String time7Val) {
        this.time7Val = time7Val;
    }

    public Date getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(Date appCreateTime) {
        this.appCreateTime = appCreateTime;
    }

    public Boolean getAppIsTest() {
        return appIsTest;
    }

    public void setAppIsTest(Boolean appIsTest) {
        this.appIsTest = appIsTest;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
