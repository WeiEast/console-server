package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * Good Luck Bro , No Bug !
 *
 * @author haojiahong
 * @date 2018/6/12
 */
public class CallbackFailureReasonVO implements Serializable {
    private static final long serialVersionUID = -3210784625380728601L;

    private Integer failureTotalCount;
    private Integer unKnownReasonCount;
    private Integer personalReasonCount;

    public CallbackFailureReasonVO() {}

    public CallbackFailureReasonVO(Integer failureTotalCount, Integer unKnownReasonCount, Integer personalReasonCount) {
        this.failureTotalCount = failureTotalCount;
        this.unKnownReasonCount = unKnownReasonCount;
        this.personalReasonCount = personalReasonCount;
    }

    public Integer getFailureTotalCount() {
        return failureTotalCount;
    }

    public void setFailureTotalCount(Integer failureTotalCount) {
        this.failureTotalCount = failureTotalCount;
    }

    public Integer getUnKnownReasonCount() {
        return unKnownReasonCount;
    }

    public void setUnKnownReasonCount(Integer unKnownReasonCount) {
        this.unKnownReasonCount = unKnownReasonCount;
    }

    public Integer getPersonalReasonCount() {
        return personalReasonCount;
    }

    public void setPersonalReasonCount(Integer personalReasonCount) {
        this.personalReasonCount = personalReasonCount;
    }
}
