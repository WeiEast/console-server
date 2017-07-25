package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haojiahong on 2017/7/21.
 */
public class AppCallbackConfigVO implements Serializable {

    private static final long serialVersionUID = -3764087130653981065L;

    private Integer id;

    private String appId;

    private String appName;//app名称

    private Byte receiver;

    private String receiverName;//通知方

    private Byte version;

    private String url;//回调地址

    private Byte retryTimes;//重试次数

    private Byte timeOut;//超时时间

    private String remark;//描述

    private Byte isNotifyCancel;

    private Byte isNotifyFailure;

    private Byte isNotifySuccess;

    private Byte notifyModel;

    private String notifyModelName;//通知方式

    private String dataSecretKey;

    private List<AppCallbackBizVO> bizTypes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Byte getReceiver() {
        return receiver;
    }

    public void setReceiver(Byte receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Byte retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Byte getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Byte timeOut) {
        this.timeOut = timeOut;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getIsNotifyCancel() {
        return isNotifyCancel;
    }

    public void setIsNotifyCancel(Byte isNotifyCancel) {
        this.isNotifyCancel = isNotifyCancel;
    }

    public Byte getIsNotifyFailure() {
        return isNotifyFailure;
    }

    public void setIsNotifyFailure(Byte isNotifyFailure) {
        this.isNotifyFailure = isNotifyFailure;
    }

    public Byte getIsNotifySuccess() {
        return isNotifySuccess;
    }

    public void setIsNotifySuccess(Byte isNotifySuccess) {
        this.isNotifySuccess = isNotifySuccess;
    }

    public Byte getNotifyModel() {
        return notifyModel;
    }

    public void setNotifyModel(Byte notifyModel) {
        this.notifyModel = notifyModel;
    }

    public String getNotifyModelName() {
        return notifyModelName;
    }

    public void setNotifyModelName(String notifyModelName) {
        this.notifyModelName = notifyModelName;
    }

    public String getDataSecretKey() {
        return dataSecretKey;
    }

    public void setDataSecretKey(String dataSecretKey) {
        this.dataSecretKey = dataSecretKey;
    }

    public List<AppCallbackBizVO> getBizTypes() {
        return bizTypes;
    }

    public void setBizTypes(List<AppCallbackBizVO> bizTypes) {
        this.bizTypes = bizTypes;
    }
}

