package com.treefinance.saas.management.console.common.domain.vo;

/**
 * Created by haojiahong on 2017/6/22.
 */
public class AppLicenseVO {

    private String appId;
    private String appName;
    private String sdkPublicKey;
    private String sdkPrivateKey;
    private String serverPublicKey;
    private String serverPrivateKey;
    private String dataSecretKey;
    private Integer createTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSdkPublicKey() {
        return sdkPublicKey;
    }

    public void setSdkPublicKey(String sdkPublicKey) {
        this.sdkPublicKey = sdkPublicKey;
    }

    public String getSdkPrivateKey() {
        return sdkPrivateKey;
    }

    public void setSdkPrivateKey(String sdkPrivateKey) {
        this.sdkPrivateKey = sdkPrivateKey;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public String getServerPrivateKey() {
        return serverPrivateKey;
    }

    public void setServerPrivateKey(String serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }

    public String getDataSecretKey() {
        return dataSecretKey;
    }

    public void setDataSecretKey(String dataSecretKey) {
        this.dataSecretKey = dataSecretKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}
