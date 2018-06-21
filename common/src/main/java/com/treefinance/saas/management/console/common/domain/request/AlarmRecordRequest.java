package com.treefinance.saas.management.console.common.domain.request;

import com.alibaba.fastjson.JSON;
import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author chengtong
 * @date 18/5/30 10:32
 */
public class AlarmRecordRequest extends PageRequest {

    /**
     * 记录编号
     * */
    private String id;

    /**
     * 对预警时间的筛选条件
     * 格式"yyyy-MM-dd hh:mm:ss"
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String startTime;

    /**
     * 对预警时间的筛选条件
     * 格式"yyyy-MM-dd hh:mm:ss"
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String endTime;

    /**
     * 对预警类型的筛选条件
     * 目前只有 operator_alarm
     * */
    private String alarmType;

    /**
     * 对预警记录摘要的筛选条件
     * 支持模糊查询
     * */
    private String summary;
    /**
     * 预警等级
     * 取值只有三个 error、warning、info
     * */
    private String level;

    /**
     *
     * 0/2/4/6/8
     * */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
