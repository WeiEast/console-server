package com.treefinance.saas.console.common.domain.request;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author chengtong
 * @date 18/5/30 10:50
 */
public class UpdateWorkOrderRequest implements Serializable {

    private Long id;

    private Integer status;

    private String opName;

    private String processorName;

    private String remark;

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
