package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/8/24.
 */
public class ChartStatDayVO implements Serializable {

    private static final long serialVersionUID = 2052095716891349412L;

    private String dataTime;//时间 x轴

    private Integer dataValue;//统计量 y轴

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataValue() {
        return dataValue;
    }

    public void setDataValue(Integer dataValue) {
        this.dataValue = dataValue;
    }
}
