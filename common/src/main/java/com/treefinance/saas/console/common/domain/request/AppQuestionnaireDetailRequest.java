package com.treefinance.saas.console.common.domain.request;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 18/8/21 14:52
 */
public class AppQuestionnaireDetailRequest implements Serializable {

    private Long detailId;
    private String content;
    private Integer detailIndex;
    private Long questionnaireId;
    private Byte category;

    private static final long serialVersionUID = 1L;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDetailIndex() {
        return detailIndex;
    }

    public void setDetailIndex(Integer detailIndex) {
        this.detailIndex = detailIndex;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }
}
