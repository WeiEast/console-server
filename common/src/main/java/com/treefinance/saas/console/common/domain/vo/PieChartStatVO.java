package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/10.
 */
public class PieChartStatVO implements Serializable {

    private static final long serialVersionUID = 4326979227284513578L;

    private String name;

    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
