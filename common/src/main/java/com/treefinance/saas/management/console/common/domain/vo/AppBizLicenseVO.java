package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/6/21.
 */
public class AppBizLicenseVO implements Serializable {

    private Byte bizType;
    private String bizName;


    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }


}
