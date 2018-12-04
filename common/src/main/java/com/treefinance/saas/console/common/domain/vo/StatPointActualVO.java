package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

public class StatPointActualVO implements Serializable {
    private static final long serialVersionUID = 8028816450482411128L;

    private String dataTime;

    private Integer dataValue;

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

    @Override
    public String toString() {
        return "StatPointActualVO{" +
                "dataTime='" + dataTime + '\'' +
                ", dataValue=" + dataValue +
                '}';
    }
}
