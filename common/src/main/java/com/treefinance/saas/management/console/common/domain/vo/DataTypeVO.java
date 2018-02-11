package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;

public class DataTypeVO implements Serializable{
    private static final long serialVersionUID = 7014433236863371439L;

    private String type;
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DataTypeVO{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
