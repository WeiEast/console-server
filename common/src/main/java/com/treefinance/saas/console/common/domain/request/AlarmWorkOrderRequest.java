package com.treefinance.saas.console.common.domain.request;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author chengtong
 * @date 18/5/30 14:28
 */
public class AlarmWorkOrderRequest  extends PageRequest {

    private Long id;

    private String dutyName;

    private String processorName;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
