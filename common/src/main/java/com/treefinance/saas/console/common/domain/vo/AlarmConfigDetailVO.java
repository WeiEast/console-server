package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmConfigDetailVO implements Serializable {
    private static final long serialVersionUID = -4623185443592479345L;

    private AlarmInfoDetailVO alarmInfo;
    private List<AlarmConstantDetailVO> alarmConstantList;
    private List<AlarmQueryDetailVO> alarmQueryList;
    private List<AlarmVariableDetailVO> alarmVariableList;
    private List<AlarmNotifyDetailVO> alarmNotifyList;
    private List<AlarmMsgDetailVO> alarmNotifyMsgList;
    private List<AlarmMsgDetailVO> alarmRecoveryMsgList;
    private List<AlarmTriggerDetailVO> alarmTriggerList;

    public AlarmInfoDetailVO getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(AlarmInfoDetailVO alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public List<AlarmConstantDetailVO> getAlarmConstantList() {
        return alarmConstantList;
    }

    public void setAlarmConstantList(List<AlarmConstantDetailVO> alarmConstantList) {
        this.alarmConstantList = alarmConstantList;
    }

    public List<AlarmQueryDetailVO> getAlarmQueryList() {
        return alarmQueryList;
    }

    public void setAlarmQueryList(List<AlarmQueryDetailVO> alarmQueryList) {
        this.alarmQueryList = alarmQueryList;
    }

    public List<AlarmVariableDetailVO> getAlarmVariableList() {
        return alarmVariableList;
    }

    public void setAlarmVariableList(List<AlarmVariableDetailVO> alarmVariableList) {
        this.alarmVariableList = alarmVariableList;
    }

    public List<AlarmNotifyDetailVO> getAlarmNotifyList() {
        return alarmNotifyList;
    }

    public void setAlarmNotifyList(List<AlarmNotifyDetailVO> alarmNotifyList) {
        this.alarmNotifyList = alarmNotifyList;
    }

    public List<AlarmTriggerDetailVO> getAlarmTriggerList() {
        return alarmTriggerList;
    }

    public void setAlarmTriggerList(List<AlarmTriggerDetailVO> alarmTriggerList) {
        this.alarmTriggerList = alarmTriggerList;
    }

    public List<AlarmMsgDetailVO> getAlarmNotifyMsgList() {
        return alarmNotifyMsgList;
    }

    public void setAlarmNotifyMsgList(List<AlarmMsgDetailVO> alarmNotifyMsgList) {
        this.alarmNotifyMsgList = alarmNotifyMsgList;
    }

    public List<AlarmMsgDetailVO> getAlarmRecoveryMsgList() {
        return alarmRecoveryMsgList;
    }

    public void setAlarmRecoveryMsgList(List<AlarmMsgDetailVO> alarmRecoveryMsgList) {
        this.alarmRecoveryMsgList = alarmRecoveryMsgList;
    }
}
