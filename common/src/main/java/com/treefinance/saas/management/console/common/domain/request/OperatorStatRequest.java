package com.treefinance.saas.management.console.common.domain.request;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.treefinance.saas.management.console.common.result.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haojiahong
 * @date 2017/11/1
 */
public class OperatorStatRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -2966509063662500226L;

    private String groupCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String appId;

    /**
     * 统计维度:0-按任务;1-按人数
     */
    private Byte statType;

    /**
     * 统计维度:来源0：所有来源；1：sdk来源；2：h5来源（必填）
     */
    private Byte sourceType;


    private String groupName;

    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Date getDataDate() {
        return dataDate;
    }

    public void setDataDate(Date dataDate) {
        this.dataDate = dataDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getStatType() {
        return statType;
    }

    public void setStatType(Byte statType) {
        this.statType = statType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "OperatorStatRequest{" +
                "groupCode='" + groupCode + '\'' +
                ", dataDate=" + dataDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", appId='" + appId + '\'' +
                ", statType=" + statType +
                ", sourceType=" + sourceType +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
