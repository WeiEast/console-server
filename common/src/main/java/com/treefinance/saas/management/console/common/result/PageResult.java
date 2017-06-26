package com.treefinance.saas.management.console.common.result;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/6/26.
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -3335196427731641046L;

    private T data;
    private StateCode stateCode;
    private String statusText;
    private Integer totalCount;
    private Long timestamp = System.currentTimeMillis();

    public PageResult() {
    }

    @Deprecated
    public PageResult(T data) {
        this.data = data;
    }

    @Deprecated
    public PageResult(T data, String statusText) {
        this.data = data;
        this.statusText = statusText;
    }

    //是否处理成功, 意味着没有业务失败和系统失败
    public boolean isSuccess() {
        return stateCode == null ? false : stateCode.getCode() >= 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public StateCode getStateCode() {
        return stateCode;
    }

    public void setStateCode(StateCode stateCode) {

        this.stateCode = stateCode;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("data", data)
                .add("stateCode", stateCode)
                .add("statusText", statusText)
                .add("timestamp", timestamp)
                .toString();
    }
}
