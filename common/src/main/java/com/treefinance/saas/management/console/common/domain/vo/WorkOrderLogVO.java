package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 18/5/31 17:47
 */
public class WorkOrderLogVO implements Serializable {

    private Long id;
    private Long orderId;
    private Long recordId;
    private String opName;
    private String opDesc;

    public Long getId() {
        return this.id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Long getRecordId() {
        return this.recordId;
    }

    public String getOpName() {
        return this.opName;
    }

    public String getOpDesc() {
        return this.opDesc;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
    }
}
