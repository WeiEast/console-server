package com.treefinance.saas.management.console.common.domain.vo;

import com.treefinance.saas.management.console.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by haojiahong on 2017/12/4.
 */
public class AllOperatorStatAccessVO implements Serializable {
    private static final long serialVersionUID = -3702552273325772879L;


    private Date dataTime;

    private String dataTimeStr;
    /**
     * 任务人数比
     */
    private BigDecimal taskUserRatio;
    private Integer entryCount;
    private Integer confirmMobileCount;
    private Integer oneClickLoginCount;
    private Integer startLoginCount;
    private Integer loginSuccessCount;
    private Integer crawlSuccessCount;
    private Integer processSuccessCount;
    private Integer callbackSuccessCount;
    /**
     * 总转化率
     */
    private BigDecimal wholeConversionRate;
    private BigDecimal confirmMobileConversionRate;
    private BigDecimal oneClickLoginConversionRate;
    private BigDecimal loginConversionRate;
    private BigDecimal loginSuccessRate;
    private BigDecimal crawlSuccessRate;
    private BigDecimal processSuccessRate;
    private BigDecimal callbackSuccessRate;

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataTimeStr() {
        if (dataTime != null) {
            return DateUtils.date2SimpleHm(this.dataTime);
        }
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }

    public BigDecimal getTaskUserRatio() {
        return taskUserRatio;
    }

    public void setTaskUserRatio(BigDecimal taskUserRatio) {
        this.taskUserRatio = taskUserRatio;
    }

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public Integer getStartLoginCount() {
        return startLoginCount;
    }

    public void setStartLoginCount(Integer startLoginCount) {
        this.startLoginCount = startLoginCount;
    }

    public Integer getLoginSuccessCount() {
        return loginSuccessCount;
    }

    public void setLoginSuccessCount(Integer loginSuccessCount) {
        this.loginSuccessCount = loginSuccessCount;
    }

    public Integer getCrawlSuccessCount() {
        return crawlSuccessCount;
    }

    public void setCrawlSuccessCount(Integer crawlSuccessCount) {
        this.crawlSuccessCount = crawlSuccessCount;
    }

    public Integer getProcessSuccessCount() {
        return processSuccessCount;
    }

    public void setProcessSuccessCount(Integer processSuccessCount) {
        this.processSuccessCount = processSuccessCount;
    }

    public Integer getCallbackSuccessCount() {
        return callbackSuccessCount;
    }

    public void setCallbackSuccessCount(Integer callbackSuccessCount) {
        this.callbackSuccessCount = callbackSuccessCount;
    }

    public BigDecimal getWholeConversionRate() {
        return wholeConversionRate;
    }

    public void setWholeConversionRate(BigDecimal wholeConversionRate) {
        this.wholeConversionRate = wholeConversionRate;
    }

    public BigDecimal getLoginConversionRate() {
        return loginConversionRate;
    }

    public void setLoginConversionRate(BigDecimal loginConversionRate) {
        this.loginConversionRate = loginConversionRate;
    }

    public BigDecimal getLoginSuccessRate() {
        return loginSuccessRate;
    }

    public void setLoginSuccessRate(BigDecimal loginSuccessRate) {
        this.loginSuccessRate = loginSuccessRate;
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

    public Integer getOneClickLoginCount() {
        return oneClickLoginCount;
    }

    public void setOneClickLoginCount(Integer oneClickLoginCount) {
        this.oneClickLoginCount = oneClickLoginCount;
    }

    public BigDecimal getOneClickLoginConversionRate() {
        return oneClickLoginConversionRate;
    }

    public void setOneClickLoginConversionRate(BigDecimal oneClickLoginConversionRate) {
        this.oneClickLoginConversionRate = oneClickLoginConversionRate;
    }

    public Integer getConfirmMobileCount() {
        return confirmMobileCount;
    }

    public void setConfirmMobileCount(Integer confirmMobileCount) {
        this.confirmMobileCount = confirmMobileCount;
    }

    public BigDecimal getConfirmMobileConversionRate() {
        return confirmMobileConversionRate;
    }

    public void setConfirmMobileConversionRate(BigDecimal confirmMobileConversionRate) {
        this.confirmMobileConversionRate = confirmMobileConversionRate;
    }
}
