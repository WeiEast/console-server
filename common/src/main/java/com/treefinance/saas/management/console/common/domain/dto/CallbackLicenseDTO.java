package com.treefinance.saas.management.console.common.domain.dto;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/25.
 */
public class CallbackLicenseDTO implements Serializable {
    private static final long serialVersionUID = -2112913019264470025L;

    private Integer callBackConfigId;
    private String dataSecretKey;
    private Long createTime;

    public Integer getCallBackConfigId() {
        return callBackConfigId;
    }

    public void setCallBackConfigId(Integer callBackConfigId) {
        this.callBackConfigId = callBackConfigId;
    }

    public String getDataSecretKey() {
        return dataSecretKey;
    }

    public void setDataSecretKey(String dataSecretKey) {
        this.dataSecretKey = dataSecretKey;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
