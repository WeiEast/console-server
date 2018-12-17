package com.treefinance.saas.console.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author  luoyihua
 * @date 2017/5/10.
 */
public enum BizTypeEnum {
    /**
     * 邮箱
     */
    EMAIL((byte)1),
    /**
     * 电商
     */
    ECOMMERCE((byte)2),
    /**
     * 运营商
     */
    OPERATOR((byte)3),
    /**
     * 公积金
     */
    FUND((byte)4);

    private byte code;

    BizTypeEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public boolean is(Byte type) {
        return type != null && type == this.code;
    }

    public boolean is(String type) {
        return this.name().equals(type);
    }

    public static Byte convert(String value) {
        if (StringUtils.isNotEmpty(value)) {
            BizTypeEnum[] values = BizTypeEnum.values();
            for (BizTypeEnum val : values) {
                if (val.name().equals(value)) {
                    return val.getCode();
                }
            }
        }
        return null;
    }

    public static boolean contains(String type) {
        BizTypeEnum[] values = values();
        for (BizTypeEnum value : values) {
            if (value.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
