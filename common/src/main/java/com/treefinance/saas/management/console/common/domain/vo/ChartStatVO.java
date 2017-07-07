package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/7/7.
 */
public class ChartStatVO implements Serializable {

    private static final long serialVersionUID = -4734492990487958188L;

    private Date dataTime;//时间 x轴

    private Integer dataValue;//统计量 y轴

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getDataValue() {
        return dataValue;
    }

    public void setDataValue(Integer dataValue) {
        this.dataValue = dataValue;
    }
}
