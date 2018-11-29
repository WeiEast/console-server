package com.treefinance.saas.management.console.biz.enums;

/**
 * Created by luoyihua on 2017/5/10.
 */
public enum BizTypeEnum {
    /**
     * 邮箱
     */
    EMAIL,
    /**
     * 电商
     */
    ECOMMERCE,
    /**
     * 运营商
     */
    OPERATOR,
    /**
     * 公积金
     */
    FUND;

    public static Byte valueOfType(BizTypeEnum typeEnum) {
        if (typeEnum == null) {
            return null;
        }
        // 业务类型，1:账单，2：电商，3:运营商，4:公积金
        switch (typeEnum) {
            case EMAIL:
                return (byte) 1;
            case ECOMMERCE:
                return (byte) 2;
            case OPERATOR:
                return (byte) 3;
            case FUND:
                return (byte) 4;
            default:
                return null;
        }
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
