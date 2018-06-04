package com.treefinance.saas.management.console.common.domain.request;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author chengtong
 * @date 18/5/30 10:32
 */
public class AlarmRecordRequest extends PageRequest {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endTime;

    private String alarmType;

    private String summary;

    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
