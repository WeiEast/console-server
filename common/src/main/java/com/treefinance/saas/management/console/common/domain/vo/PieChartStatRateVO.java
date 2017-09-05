package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by haojiahong on 2017/9/4.
 */
public class PieChartStatRateVO implements Serializable {
    private static final long serialVersionUID = 3016835174023914008L;

    private String name;

    private BigDecimal value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
