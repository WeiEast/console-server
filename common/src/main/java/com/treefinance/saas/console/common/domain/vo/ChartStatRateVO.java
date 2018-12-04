package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by haojiahong on 2017/7/7.
 */
public class ChartStatRateVO implements Serializable {

    private static final long serialVersionUID = -4734492990487958188L;

    public ChartStatRateVO() {
    }

    public ChartStatRateVO(Date dataTime, BigDecimal dataValue) {
        this.dataTime = dataTime;
        this.dataValue = dataValue;
    }

    private Date dataTime;//时间 x轴

    private BigDecimal dataValue;//统计量 y轴

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }
}
