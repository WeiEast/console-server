package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by haojiahong on 2017/8/16.
 */
public class TaskLogVO implements Serializable {

    private static final long serialVersionUID = -5460741216383181048L;

    private Long id;
    private String msg;
    private Date occurTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }
}
