package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmConstantDetailVO implements Serializable {
    private static final long serialVersionUID = -5953017975007597412L;

    private Long id;
    private String name;
    private String code;
    private String value;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
