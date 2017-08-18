package com.treefinance.saas.management.console.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/18.
 */
public class TaskCallbackLogDTO implements Serializable {

    private static final long serialVersionUID = -6030254946809989645L;

    private Long id;
    private Long taskId;
    private String url;
    private String requestParam;
    private String plainRequestParam;
    private String responseData;
    private Integer consumeTime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public Integer getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Integer consumeTime) {
        this.consumeTime = consumeTime;
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

    public String getPlainRequestParam() {
        return plainRequestParam;
    }

    public void setPlainRequestParam(String plainRequestParam) {
        this.plainRequestParam = plainRequestParam;
    }
}
