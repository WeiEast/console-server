package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: chengtong
 * @Date: 18/2/27 09:52
 */
public class OperatorStatDayConvertRateVo implements Serializable {

    private static final long serialVersionUID = 854680180124395415L;

    private BigDecimal dataValue;

    private String dataTime;

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
}
