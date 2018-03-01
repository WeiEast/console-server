package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: chengtong
 * @Date: 18/2/27 09:52
 */
public class OperatorStatDayConvertRateVo implements Serializable {

    private static final long serialVersionUID = 854680180124395415L;



    private BigDecimal convertRate;

    private String date;

    public BigDecimal getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(BigDecimal convertRate) {
        this.convertRate = convertRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
