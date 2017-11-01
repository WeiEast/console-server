package com.treefinance.saas.management.console.common.domain.vo;

import com.treefinance.saas.management.console.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author haojiahong
 * @date 2017/11/1
 */
public class AllOperatorStatDayAccessVO implements Serializable {

    private static final long serialVersionUID = 7574871619299384612L;

    private Date dataTime;

    private String dataTimeStr;

    private BigDecimal loginConversionRate;

    private BigDecimal crawlSuccessRate;

    private BigDecimal processSuccessRate;

    private BigDecimal callbackSuccessRate;

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public BigDecimal getLoginConversionRate() {
        return loginConversionRate;
    }

    public void setLoginConversionRate(BigDecimal loginConversionRate) {
        this.loginConversionRate = loginConversionRate;
    }

    public BigDecimal getCrawlSuccessRate() {
        return crawlSuccessRate;
    }

    public void setCrawlSuccessRate(BigDecimal crawlSuccessRate) {
        this.crawlSuccessRate = crawlSuccessRate;
    }

    public BigDecimal getProcessSuccessRate() {
        return processSuccessRate;
    }

    public void setProcessSuccessRate(BigDecimal processSuccessRate) {
        this.processSuccessRate = processSuccessRate;
    }

    public BigDecimal getCallbackSuccessRate() {
        return callbackSuccessRate;
    }

    public void setCallbackSuccessRate(BigDecimal callbackSuccessRate) {
        this.callbackSuccessRate = callbackSuccessRate;
    }

    public String getDataTimeStr() {
        if (dataTime != null) {
            return DateUtils.date2Ymd(this.dataTime);
        }
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }
}
