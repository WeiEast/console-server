package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmNotifyDetailVO implements Serializable {
    private static final long serialVersionUID = 4650947758979316850L;

    private Long id;
    private String alarmLevel;
    private String wechatSwitch;
    private String smsSwitch;
    private String emailSwitch;
    private String ivrSwitch;
    private Byte receiverType;
    private String receiverIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getWechatSwitch() {
        return wechatSwitch;
    }

    public void setWechatSwitch(String wechatSwitch) {
        this.wechatSwitch = wechatSwitch;
    }

    public String getSmsSwitch() {
        return smsSwitch;
    }

    public void setSmsSwitch(String smsSwitch) {
        this.smsSwitch = smsSwitch;
    }

    public String getEmailSwitch() {
        return emailSwitch;
    }

    public void setEmailSwitch(String emailSwitch) {
        this.emailSwitch = emailSwitch;
    }

    public String getIvrSwitch() {
        return ivrSwitch;
    }

    public void setIvrSwitch(String ivrSwitch) {
        this.ivrSwitch = ivrSwitch;
    }

    public Byte getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Byte receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverIds() {
        return receiverIds;
    }

    public void setReceiverIds(String receiverIds) {
        this.receiverIds = receiverIds;
    }
}

