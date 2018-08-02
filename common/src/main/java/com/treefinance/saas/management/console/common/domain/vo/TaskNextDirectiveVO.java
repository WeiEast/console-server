package com.treefinance.saas.management.console.common.domain.vo;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:guoguoyun
 * @date:Created in 2018/8/2上午10:55
 */
public class TaskNextDirectiveVO implements Serializable {


    private Long id;


    private Long taskId;


    private String directive;


    private String remark;


    private Date createTime;


    private Date lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "TaskNextDirectiveVO{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", directive='" + directive + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
