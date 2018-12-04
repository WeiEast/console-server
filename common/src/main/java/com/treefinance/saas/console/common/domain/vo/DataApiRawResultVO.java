package com.treefinance.saas.console.common.domain.vo;

import java.util.Date;

public class DataApiRawResultVO {

    private String appId;
    private Long taskId;
    private Date execAt;
    private Long execDurationInMills;
    private Boolean isSuccess;
    private Long internalErrorCode;
    private String paramsStorePath;
    private String requesetJson;
    private String responseJson;
    private String errorMsg;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Date getExecAt() {
        return execAt;
    }

    public void setExecAt(Date execAt) {
        this.execAt = execAt;
    }

    public Long getExecDurationInMills() {
        return execDurationInMills;
    }

    public void setExecDurationInMills(Long execDurationInMills) {
        this.execDurationInMills = execDurationInMills;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean success) {
        isSuccess = success;
    }

    public Long getInternalErrorCode() {
        return internalErrorCode;
    }

    public void setInternalErrorCode(Long internalErrorCode) {
        this.internalErrorCode = internalErrorCode;
    }

    public String getParamsStorePath() {
        return paramsStorePath;
    }

    public void setParamsStorePath(String paramsStorePath) {
        this.paramsStorePath = paramsStorePath;
    }

    public String getRequesetJson() {
        return requesetJson;
    }

    public void setRequesetJson(String requesetJson) {
        this.requesetJson = requesetJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
