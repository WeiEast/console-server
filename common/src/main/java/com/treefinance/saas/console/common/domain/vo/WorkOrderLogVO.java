package com.treefinance.saas.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/5/31 17:47
 */
public class WorkOrderLogVO extends BaseVO {

    private Long id;
    private Long orderId;
    private Long recordId;
    private String opName;
    private String opDesc;
    private String opTime;

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

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
