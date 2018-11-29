package com.treefinance.saas.management.console.biz.enums;

/**
 * Created by yh-treefinance on 2017/6/7.
 */
public enum ETaskStatusEnum {
    RUNNING((byte) 0, "进行中"),
    CANCEL((byte) 1, "取消"),
    SUCCESS((byte) 2, "成功"),
    FAIL((byte) 3, "失败");

    private Byte status;
    private String name;

    ETaskStatusEnum(Byte status, String name) {
        this.status = status;
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static String getNameByStatus(Byte status) {
        for (ETaskStatusEnum taskStatus : ETaskStatusEnum.values()) {
            if (taskStatus.getStatus().equals(status)) {
                return taskStatus.getName();
            }
        }
        return "";
    }
}
