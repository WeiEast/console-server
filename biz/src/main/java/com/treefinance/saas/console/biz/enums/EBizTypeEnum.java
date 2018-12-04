package com.treefinance.saas.console.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by luoyihua on 2017/4/27.
 */
public enum EBizTypeEnum {
    EMAIL("EMAIL", (byte) 1, "邮箱账单"),
    ECOMMERCE("ECOMMERCE", (byte) 2, "电商"),
    OPERATOR("OPERATOR", (byte) 3, "运营商"),
    FUND("FUND", (byte) 4, "公积金");

    private Byte code;
    private String text;
    private String name;

    EBizTypeEnum(String text, Byte code, String name) {
        this.code = code;
        this.text = text;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Byte getCode(String text) {
        if (StringUtils.isNotEmpty(text)) {
            for (EBizTypeEnum item : EBizTypeEnum.values()) {
                if (text.equals(item.getText())) {
                    return item.getCode();
                }
            }
        }
        return -1;
    }

    public static String getName(Byte code) {
        for (EBizTypeEnum item : EBizTypeEnum.values()) {
            if (code.equals(item.getCode())) {
                return item.getName();
            }
        }
        return "";
    }


}
