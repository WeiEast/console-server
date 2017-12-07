package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author haojiahong
 * @date 2017/11/1
 */
public class OperatorStatDayAccessDetailVO implements Serializable {

    private static final long serialVersionUID = 5314057593105667410L;

    private String groupName;

    private Date dataTime;

    private String dataTimeStr;

    private Integer confirmMobileCount;
    private Integer startLoginCount;
    private Integer loginSuccessCount;
    private Integer crawlSuccessCount;
    private Integer processSuccessCount;
    private Integer callbackSuccessCount;
    private BigDecimal loginConversionRate;
    private BigDecimal loginSuccessRate;
    private BigDecimal crawlSuccessRate;
    private BigDecimal processSuccessRate;
    private BigDecimal callbackSuccessRate;
    private BigDecimal taskUserRatio;

    private List<OperatorStatDayAccessDetailVO> children;

    public Integer getStartLoginCount() {
        return startLoginCount;
    }

    public void setStartLoginCount(Integer startLoginCount) {
        this.startLoginCount = startLoginCount;
    }

    public BigDecimal getLoginSuccessRate() {
        return loginSuccessRate;
    }

    public void setLoginSuccessRate(BigDecimal loginSuccessRate) {
        this.loginSuccessRate = loginSuccessRate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getConfirmMobileCount() {
        return confirmMobileCount;
    }

    public void setConfirmMobileCount(Integer confirmMobileCount) {
        this.confirmMobileCount = confirmMobileCount;
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

    public List<OperatorStatDayAccessDetailVO> getChildren() {
        return children;
    }

    public void setChildren(List<OperatorStatDayAccessDetailVO> children) {
        this.children = children;
    }

    public String getDataTimeStr() {
        return dataTimeStr;
    }

    public void setDataTimeStr(String dataTimeStr) {
        this.dataTimeStr = dataTimeStr;
    }

    public Integer getCallbackSuccessCount() {
        return callbackSuccessCount;
    }

    public void setCallbackSuccessCount(Integer callbackSuccessCount) {
        this.callbackSuccessCount = callbackSuccessCount;
    }

    public BigDecimal getCallbackSuccessRate() {
        return callbackSuccessRate;
    }

    public void setCallbackSuccessRate(BigDecimal callbackSuccessRate) {
        this.callbackSuccessRate = callbackSuccessRate;
    }

    public BigDecimal getTaskUserRatio() {
        return taskUserRatio;
    }

    public void setTaskUserRatio(BigDecimal taskUserRatio) {
        this.taskUserRatio = taskUserRatio;
    }
}
