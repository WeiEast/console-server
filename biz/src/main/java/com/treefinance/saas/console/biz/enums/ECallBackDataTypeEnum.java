package com.treefinance.saas.console.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by haojiahong on 2017/10/11.
 */
public enum ECallBackDataTypeEnum {
    MAIN((byte) 0, "主流程数据"),
    SHIPPING_ADDRESS((byte) 1, "收货地址数据"),
    OPERATOR_FLOW((byte) 2, "运营商流量数据");

    private Byte code;
    private String text;

    ECallBackDataTypeEnum(Byte code, String text) {
        this.code = code;
        this.text = text;
    }

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

    public static Byte getCode(String text) {
        if (StringUtils.isNotEmpty(text)) {
            for (ECallBackDataTypeEnum item : ECallBackDataTypeEnum.values()) {
                if (text.equals(item.getText())) {
                    return item.getCode();
                }
            }
        }
        return -1;
    }

    public static String getText(Byte code) {
        for (ECallBackDataTypeEnum item : ECallBackDataTypeEnum.values()) {
            if (code.equals(item.getCode())) {
                return item.getText();
            }
        }
        return "";
    }
}
