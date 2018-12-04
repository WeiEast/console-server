package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

public class StatPointDayVO implements Serializable{

    private static final long serialVersionUID = 6237612330360472023L;

    private String dataTime;

    private Integer dataValue;

    private Integer totalValue;

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

    public Integer getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "StatPointDayVO{" +
                "dataTime='" + dataTime + '\'' +
                ", dataValue=" + dataValue +
                ", totalValue=" + totalValue +
                '}';
    }
}
