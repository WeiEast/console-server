package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/11/21.
 */
public class OssCallbackDataVO implements Serializable {
    private static final long serialVersionUID = 854680180124395415L;

    private Long id;
    private String uniqueId;
    private String appName;
    private Long taskId;
    private String accountNo;
    private String groupName;
    private Date taskStartTime;
    private Date taskEndTime;
    private String taskStatusName;
    private String callbackTypeName;
    private String callbackRequestParam;
    private String callbackResponseData;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }

    public String getCallbackRequestParam() {
        return callbackRequestParam;
    }

    public void setCallbackRequestParam(String callbackRequestParam) {
        this.callbackRequestParam = callbackRequestParam;
    }

    public String getCallbackResponseData() {
        return callbackResponseData;
    }

    public void setCallbackResponseData(String callbackResponseData) {
        this.callbackResponseData = callbackResponseData;
    }

    public String getCallbackTypeName() {
        return callbackTypeName;
    }

    public void setCallbackTypeName(String callbackTypeName) {
        this.callbackTypeName = callbackTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
