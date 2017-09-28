package com.treefinance.saas.management.console.common.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by haojiahong on 2017/9/28.
 */
public enum EServiceTag {
    DEV("dev", "开发环境"),
    TEST("test", "测试环境"),
    APPROACH("approach", "准生产环境"),
    PRE_PRODUCT("pre-product", "预发布环境"),
    PRODUCT("product", "生产环境");

    private String tag;
    private String desc;

    EServiceTag(String tag, String desc) {
        this.tag = tag;
        this.desc = desc;
    }

    public static String getDesc(String tag) {
        if (StringUtils.isNotEmpty(tag)) {
            for (EServiceTag item : EServiceTag.values()) {
                if (tag.equals(item.getTag())) {
                    return item.getDesc();
                }
            }
        }
        return null;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
