package com.treefinance.saas.management.console.common.domain.vo;

import java.util.Date;

/**
 * @author chengtong
 * @date 18/5/31 17:06
 */
public class AlarmRecordVO extends BaseVO {

    /**记录编号*/
    private Long id;
    /**预警的数据时间*/
    private Date dataTime;
    /**记录状态*/
    private Integer isProcessed;
    /**处理状态描述*/
    private String processDesc;
    /**预警等级*/
    private String level;
    /**预警类型*/
    private String alarmType;
    /**摘要信息*/
    private String summary;
    /**预警内容*/
    private String content;
    /**处理人员名称*/
    private String processorName;
    /**值班人员名字*/
    private String dutyName;
    /**处理描述*/
    private String desc;
    /**预警工单状态*/
    private Integer orderStatus;
    /**工单状态描述*/
    private String orderStatusDesc;
    /**开始时间*/
    private Date startTime;
    /**结束时间*/
    private Date endTime;
    /**持续时间*/
    private Date continueTime;
    /**工单编号*/
    private Long orderId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getIsProcessed() {
        return isProcessed;
    }

    public void setIsProcessed(Integer isProcessed) {
        this.isProcessed = isProcessed;
    }

    public String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(Date continueTime) {
        this.continueTime = continueTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
