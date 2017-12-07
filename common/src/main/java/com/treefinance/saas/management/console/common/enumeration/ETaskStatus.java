package com.treefinance.saas.management.console.common.enumeration;

/**
 * Created by yh-treefinance on 2017/6/7.
 */
public enum ETaskStatus {
    RUNNING((byte) 0, "进行中"),
    CANCEL((byte) 1, "取消"),
    SUCCESS((byte) 2, "成功"),
    FAIL((byte) 3, "失败");

    private Byte status;
    private String name;

    ETaskStatus(Byte status, String name) {
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
        for (ETaskStatus taskStatus : ETaskStatus.values()) {
            if (taskStatus.getStatus().equals(status)) {
                return taskStatus.getName();
            }
        }
        return "";
    }
}
