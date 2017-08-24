package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 按天统计的图表(时间只显示YYYY-MM-DD)
 * Created by haojiahong on 2017/8/24.
 */
public class ChartStatDayRateVO implements Serializable {

    private static final long serialVersionUID = 7049074425488093102L;

    private String dataTime;//时间 x轴

    private BigDecimal dataValue;//统计量 y轴

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }
}
