package com.treefinance.saas.console.common.domain.request;

import java.io.Serializable;

/**
 * Created by yh-treefinance on 2018/5/22.
 */
public class TestExpressionRequest implements Serializable {

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 基础数据json
     */
    private String dataJson;

    /**
     * 表达式
     */
    private String expression;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
