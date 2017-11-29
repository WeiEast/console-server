package com.treefinance.saas.management.console.common.enumeration;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 任务的失败环节枚举,现在是根据数据库中查询到的统计了几种
 * Created by haojiahong on 2017/11/16.
 */
public enum ETaskErrorStep {

    QR_CODE_VALIDATE_FAIL("二维码验证失败", 1),
    MESSAGE_CODE_VALIDATE_FAIL("短信验证码校验失败", 1),
    LOGIN_FAIL("登陆失败", 2),
    CRAWL_FAIL("抓取失败", 3),
    DATA_PREPROCESSED_FAIL("数据预处理失败", 4),
    DATA_PROCESS_FAIL("数据处理失败", 5),
    DATA_SAVE_FAIL("数据保存失败", 6),
    CALLBACK_FAIL("回调通知失败", 7),
    TASK_TIMEOUT("任务超时", 8);

    private String text;//错误环节具体信息
    private Integer order;//错误环节出现顺序

    ETaskErrorStep(String text, Integer order) {
        this.text = text;
        this.order = order;
    }


    public static List<String> getTaskErrorStepList() {
        List<String> list = Lists.newArrayList();
        for (ETaskErrorStep item : ETaskErrorStep.values()) {
            list.add(item.getText());
        }
        return list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
