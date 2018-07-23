package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmInfoDetailVO implements Serializable {
    private static final long serialVersionUID = -1129711033388621926L;

    private Long id;
    private String name;
    private Byte runEnv;
    private String alarmSwitch;
    private String runInterval;
    private Integer timeInterval;

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
}
