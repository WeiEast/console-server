package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/15.
 */
public class TaskVO implements Serializable {

    private static final long serialVersionUID = 2447814470266258666L;

    private Long id;
    private String appId;
    private String appName;
    private String uniqueId;
    private String accountNo;
    private Byte status;
    private String operatorName;
    private String callbackRequest;
    private String callbackResponse;
    private String webSite;
    private Date createTime;
    private Date lastUpdateTime;
    private Boolean canDownload;
    private Long callbackLogId;

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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

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

    public String getCallbackRequest() {
        return callbackRequest;
    }

    public void setCallbackRequest(String callbackRequest) {
        this.callbackRequest = callbackRequest;
    }

    public String getCallbackResponse() {
        return callbackResponse;
    }

    public void setCallbackResponse(String callbackResponse) {
        this.callbackResponse = callbackResponse;
    }

    public Boolean getCanDownload() {
        return canDownload;
    }

    public void setCanDownload(Boolean canDownload) {
        this.canDownload = canDownload;
    }

    public Long getCallbackLogId() {
        return callbackLogId;
    }

    public void setCallbackLogId(Long callbackLogId) {
        this.callbackLogId = callbackLogId;
    }
}
