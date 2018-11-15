package com.treefinance.saas.management.console.common.enumeration;

/**
 * Created by luoyihua on 2017/5/10.
 */
public enum BizTypeEnum {
    EMAIL, ECOMMERCE, OPERATOR, FUND;

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
        }
        return null;
    }
}
