package com.treefinance.saas.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/5/31 17:31
 */
public class AlarmWorkOrderVO extends BaseVO{

    private Long id;
    private Long recordId;
    private Integer status;
    private String dutyName;
    private String processorName;
    private String remark;
    private String statusDesc;

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getId() {
        return this.id;
    }

    public Long getRecordId() {
        return this.recordId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getDutyName() {
        return this.dutyName;
    }

    public String getProcessorName() {
        return this.processorName;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getStatusDesc() {
        return this.statusDesc;
    }
}
