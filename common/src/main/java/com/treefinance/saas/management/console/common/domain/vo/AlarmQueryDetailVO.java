package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmQueryDetailVO implements Serializable {
    private static final long serialVersionUID = 1637221013430205960L;

    private Long id;
    private Integer queryIndex;
    private String resultCode;
    private String querySql;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQueryIndex() {
        return queryIndex;
    }

    public void setQueryIndex(Integer queryIndex) {
        this.queryIndex = queryIndex;
    }
}
