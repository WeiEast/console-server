package com.treefinance.saas.console.biz.enums;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/23
 */
public enum ESaasEnvEnum {
    ALL((byte) 0, "所有环境"),
    PRODUCT((byte) 1, "生产环境"),
    PRE_PRODUCT((byte) 2, "预发布环境");

    private Byte code;
    private String name;

    ESaasEnvEnum(Byte code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String getName(Byte code) {
        for (ESaasEnvEnum item : ESaasEnvEnum.values()) {
            if (code.equals(item.getCode())) {
                return item.getName();
            }
        }
        return "";
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
