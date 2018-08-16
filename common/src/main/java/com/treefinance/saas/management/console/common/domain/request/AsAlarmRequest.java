package com.treefinance.saas.management.console.common.domain.request;


import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author:guoguoyun
 * @date:Created in 2018/7/18下午5:21
 */
public class AsAlarmRequest extends PageRequest {

    /**
     * 预警配置ID
     */
    private  Long id ;
    /**
     * 预警名称
     */
    private  String name;
    /**
     * 预警执行环境(0-所有，1-生产，2-预发布)
     */
    private Byte runEnv;
    /**
     * 预警开关
     */
    private String alarmSwitch;
    /**
     * 预警执行时间
     */
    private String runInterval;
    /**
     * 预警时间间隔
     */
    private Integer timeInterval;

    /**
     *查询预警执行初始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startDate;
    /**
     *查询预警执行结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(Byte runEnv) {
        this.runEnv = runEnv;
    }

    public String getAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(String alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }

    public String getRunInterval() {
        return runInterval;
    }

    public void setRunInterval(String runInterval) {
        this.runInterval = runInterval;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
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
}
