package com.treefinance.saas.console.common.domain.request;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengtong
 * @date 18/8/21 14:52
 */
public class AppQuestionnaireRequest implements Serializable {

    private Long id;
    private String appId;
    private String questionnaireCode;
    private String questionnaireName;
    private Byte bizType;
    private String step;
    private List<AppQuestionnaireDetailRequest> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getQuestionnaireCode() {
        return questionnaireCode;
    }

    public void setQuestionnaireCode(String questionnaireCode) {
        this.questionnaireCode = questionnaireCode;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public List<AppQuestionnaireDetailRequest> getDetails() {
        return details;
    }

    public void setDetails(List<AppQuestionnaireDetailRequest> details) {
        this.details = details;
    }
}
