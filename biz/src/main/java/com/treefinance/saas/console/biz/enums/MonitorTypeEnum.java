package com.treefinance.saas.console.biz.enums;

/**
 * @author Jerry
 * @date 2018/11/27 15:16
 */
public enum MonitorTypeEnum {
    /**
     * 任务量监控
     */
    TASK("任务量"),
    /**
     * 访问量监控
     */
    ACCESS("访问量"),
    /**
     * 合计访问量监控
     */
    TOTAL("");

    private String name;

    MonitorTypeEnum(String desc) {
        this.name = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return name.isEmpty() ? name : name + "监控";
    }

    public String getTotalDesc() {
        return this == TOTAL ? "合计" : "系统总" + getDesc();
    }
}
