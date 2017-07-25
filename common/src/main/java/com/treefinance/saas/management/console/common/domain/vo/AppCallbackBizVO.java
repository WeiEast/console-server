package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/24.
 */
public class AppCallbackBizVO implements Serializable {

    private static final long serialVersionUID = -7259543950524341341L;

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
