package com.treefinance.saas.management.console.common.enumeration;

/**
 * console服务权限类型转换为saas的服务权限类型
 * -1,-2表示:还未在console维护的服务权限
 * Created by haojiahong on 2017/7/6.
 */
public enum EBizType4Monitor {
    TOTAL("TOTAL", (byte) -1, "合计", (byte) 0, "合计"),
    EMAIL("EMAIL", (byte) 1, "邮箱账单", (byte) 3, "邮箱"),
    ECOMMERCE("ECOMMERCE", (byte) 2, "电商", (byte) 2, "电商"),
    OPERATOR("OPERATOR", (byte) 3, "运营商", (byte) 4, "运营商"),
    BANK("BANK", (byte) -2, "银行", (byte) 1, "银行");


    private Byte code;
    private String text;
    private String name;
    private Byte monitorCode;
    private String monitorName;

    EBizType4Monitor(String text, Byte code, String name, Byte monitorCode, String monitorName) {
        this.code = code;
        this.text = text;
        this.name = name;
        this.monitorCode = monitorCode;
        this.monitorName = monitorName;
    }

    public static Byte getMonitorCode(Byte code) {
        for (EBizType4Monitor item : EBizType4Monitor.values()) {
            if (code.equals(item.getCode())) {
                return item.getMonitorCode();
            }
        }
        return (byte) -1;
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

    public Byte getMonitorCode() {
        return monitorCode;
    }

    public void setMonitorCode(Byte monitorCode) {
        this.monitorCode = monitorCode;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
}
