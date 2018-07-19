package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:guoguoyun
 * @date:Created in 2018/4/27下午4:17
 */
public class StatTemplateVO implements Serializable{
    private Long id;


    private String templateCode;


    private String templateName;


    private Byte status;


    private String statCron;

    private Integer expressionType;


    private Long basicDataId;


    private String basicDataFilter;

    private String dataObject;


    private Integer effectiveTime;


    private String flushDataCron;


    private Date createTime;


    private Date lastUpdateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatCron() {
        return statCron;
    }

    public void setStatCron(String statCron) {
        this.statCron = statCron;
    }

    public Integer getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Integer expressionType) {
        this.expressionType = expressionType;
    }

    public Long getBasicDataId() {
        return basicDataId;
    }

    public void setBasicDataId(Long basicDataId) {
        this.basicDataId = basicDataId;
    }

    public String getBasicDataFilter() {
        return basicDataFilter;
    }

    public void setBasicDataFilter(String basicDataFilter) {
        this.basicDataFilter = basicDataFilter;
    }

    public String getDataObject() {
        return dataObject;
    }

    public void setDataObject(String dataObject) {
        this.dataObject = dataObject;
    }

    public Integer getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Integer effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getFlushDataCron() {
        return flushDataCron;
    }

    public void setFlushDataCron(String flushDataCron) {
        this.flushDataCron = flushDataCron;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "StatTemplateVO{" +
                "id=" + id +
                ", templateCode='" + templateCode + '\'' +
                ", templateName='" + templateName + '\'' +
                ", status=" + status +
                ", statCron='" + statCron + '\'' +
                ", expressionType=" + expressionType +
                ", basicDataId=" + basicDataId +
                ", basicDataFilter='" + basicDataFilter + '\'' +
                ", dataObject='" + dataObject + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", flushDataCron='" + flushDataCron + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
