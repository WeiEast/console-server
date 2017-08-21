package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/21.
 */
public class TaskDetailVO implements Serializable {

    private static final long serialVersionUID = -7588806945704324116L;

    private Long id;

    private String uniqueId;

    private String appId;

    private Byte bizType;

    private String bizTypeName;

    private String websiteDetailName;

    private String msg;//失败环节

    private String errorMsg; //失败信息

    private Date occurTime; //发生时间

    private String occurTimeStr;//发生时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName;
    }

    public String getWebsiteDetailName() {
        return websiteDetailName;
    }

    public void setWebsiteDetailName(String websiteDetailName) {
        this.websiteDetailName = websiteDetailName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public String getOccurTimeStr() {
        return occurTimeStr;
    }

    public void setOccurTimeStr(String occurTimeStr) {
        this.occurTimeStr = occurTimeStr;
    }
}
