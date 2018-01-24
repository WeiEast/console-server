package com.treefinance.saas.management.console.common.domain.request;

import com.treefinance.saas.management.console.common.result.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DsDataApiRequest extends PageRequest{

    private String dataApiNameType;

    private Long taskId;

    private Boolean isSuccess;

    private Long internalErrorCode;

    private String appId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDataApiNameType() {
        return dataApiNameType;
    }

    public void setDataApiNameType(String dataApiNameType) {
        this.dataApiNameType = dataApiNameType;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getInternalErrorCode() {
        return internalErrorCode;
    }

    public void setInternalErrorCode(Long internalErrorCode) {
        this.internalErrorCode = internalErrorCode;
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
