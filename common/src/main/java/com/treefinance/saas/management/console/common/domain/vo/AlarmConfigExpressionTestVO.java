package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author haojiahong
 * @date 2018/7/23
 */
public class AlarmConfigExpressionTestVO implements Serializable {
    private static final long serialVersionUID = -6736784924186064593L;

    private Byte expressionType;
    private String expressionTypeDesc;
    private String expressionCode;
    private String expressionContent;

    private AlarmInfoDetailVO alarmInfo;
    private List<AlarmConstantDetailVO> alarmConstantList;
    private List<AlarmQueryDetailVO> alarmQueryList;
    private List<AlarmVariableDetailVO> alarmVariableList;
    private List<AlarmNotifyDetailVO> alarmNotifyList;
    private AlarmMsgDetailVO alarmMsg;
    private List<AlarmTriggerDetailVO> alarmTriggerList;

    public Byte getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Byte expressionType) {
        this.expressionType = expressionType;
    }

    public String getExpressionTypeDesc() {
        return expressionTypeDesc;
    }

    public void setExpressionTypeDesc(String expressionTypeDesc) {
        this.expressionTypeDesc = expressionTypeDesc;
    }

    public String getExpressionContent() {
        return expressionContent;
    }

    public void setExpressionContent(String expressionContent) {
        this.expressionContent = expressionContent;
    }

    public String getExpressionCode() {
        return expressionCode;
    }

    public void setExpressionCode(String expressionCode) {
        this.expressionCode = expressionCode;
    }

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

    public AlarmMsgDetailVO getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(AlarmMsgDetailVO alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public List<AlarmTriggerDetailVO> getAlarmTriggerList() {
        return alarmTriggerList;
    }

    public void setAlarmTriggerList(List<AlarmTriggerDetailVO> alarmTriggerList) {
        this.alarmTriggerList = alarmTriggerList;
    }
}