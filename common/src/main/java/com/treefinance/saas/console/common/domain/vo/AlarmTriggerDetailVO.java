package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author haojiahong
 * @date 2018/7/20
 */
public class AlarmTriggerDetailVO implements Serializable {
    private static final long serialVersionUID = -6160622307522672633L;

    private Long id;
    private Integer triggerIndex;
    private String name;
    private Byte status;
    private String infoTrigger;
    private String warningTrigger;
    private String errorTrigger;
    private String recoveryTrigger;
    private String recoveryMessageTemplate;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getInfoTrigger() {
        return infoTrigger;
    }

    public void setInfoTrigger(String infoTrigger) {
        this.infoTrigger = infoTrigger;
    }

    public String getWarningTrigger() {
        return warningTrigger;
    }

    public void setWarningTrigger(String warningTrigger) {
        this.warningTrigger = warningTrigger;
    }

    public String getErrorTrigger() {
        return errorTrigger;
    }

    public void setErrorTrigger(String errorTrigger) {
        this.errorTrigger = errorTrigger;
    }

    public String getRecoveryTrigger() {
        return recoveryTrigger;
    }

    public void setRecoveryTrigger(String recoveryTrigger) {
        this.recoveryTrigger = recoveryTrigger;
    }

    public String getRecoveryMessageTemplate() {
        return recoveryMessageTemplate;
    }

    public void setRecoveryMessageTemplate(String recoveryMessageTemplate) {
        this.recoveryMessageTemplate = recoveryMessageTemplate;
    }

    public Integer getTriggerIndex() {
        return triggerIndex;
    }

    public void setTriggerIndex(Integer triggerIndex) {
        this.triggerIndex = triggerIndex;
    }
}
