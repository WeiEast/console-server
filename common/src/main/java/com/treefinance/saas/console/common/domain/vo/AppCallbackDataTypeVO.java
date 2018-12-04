package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/10/11.
 */
public class AppCallbackDataTypeVO implements Serializable {
    private static final long serialVersionUID = 1145654722761639568L;

    private Byte code;

    private String text;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
