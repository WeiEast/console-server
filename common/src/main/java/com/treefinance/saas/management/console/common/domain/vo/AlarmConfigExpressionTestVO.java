package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/23
 */
public class AlarmConfigExpressionTestVO implements Serializable {
    private static final long serialVersionUID = -6736784924186064593L;

    private Byte expressionType;
    private String expressionTypeDesc;
    private String expressionContent;
    private AlarmConfigDetailVO expressionNeededInfo;

    public Byte getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Byte expressionType) {
        this.expressionType = expressionType;
    }

    public String getExpressionTypeDesc() {
        return expressionTypeDesc;
    }

    public void setExpressionTypeDesc(String expressionTypeDesc) {
        this.expressionTypeDesc = expressionTypeDesc;
    }

    public String getExpressionContent() {
        return expressionContent;
    }

    public void setExpressionContent(String expressionContent) {
        this.expressionContent = expressionContent;
    }

    public AlarmConfigDetailVO getExpressionNeededInfo() {
        return expressionNeededInfo;
    }

    public void setExpressionNeededInfo(AlarmConfigDetailVO expressionNeededInfo) {
        this.expressionNeededInfo = expressionNeededInfo;
    }
}
