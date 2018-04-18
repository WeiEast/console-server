package com.treefinance.saas.management.console.common.domain.request;


import com.treefinance.saas.knife.request.PageRequest;

/**
 * Created by haojiahong on 2017/11/21.
 */
public class OssDataRequest extends PageRequest {

    private String appName;
    private String uniqueId;
    private Long taskId;
    private String accountNo;
    private Byte type;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
