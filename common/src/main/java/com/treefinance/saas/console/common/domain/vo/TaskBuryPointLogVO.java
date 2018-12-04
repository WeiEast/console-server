package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:guoguoyun
 * @date:Created in 2018/6/27上午10:42
 */
public class TaskBuryPointLogVO implements Serializable {


    private Long id;

    private Long taskId;

    private String appId;

    private String code;

    private String codeMessage;

    private Date createTime;


    private Date lastUpdateTime;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getTaskId() {
        return taskId;
    }


    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    public String getAppId() {
        return appId;
    }


    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(String codeMessage) {
        this.codeMessage = codeMessage;
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

    @Override
    public String toString() {
        return "TaskBuryPointLogVO{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", appId='" + appId + '\'' +
                ", code='" + code + '\'' +
                ", codeMessage='" + codeMessage + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
