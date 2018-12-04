package com.treefinance.saas.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/7/19 16:58
 */
public class AlarmConfigVO extends BaseVO {
    /**
     * 预警配置ID
     */
    private Long id;
    /**
     * 预警名称
     */
    private String name;
    /**
     * 预警执行环境(0-所有，1-生产，2-预发布)
     */
    private Byte runEnv;

    private String runEnvDesc;
    /**
     * 预警开关
     */
    private String alarmSwitch;
    /**
     * 预警执行时间
     */
    private String runInterval;
    /**
     * 预警时间间隔
     */
    private Integer timeInterval;
    /**
     * 通知消息标题
     * */
    private String titleTemplate;
    /**
     * 通知消息
     * */
    private String bodyTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(Byte runEnv) {
        this.runEnv = runEnv;
    }

    public String getAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(String alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }

    public String getRunInterval() {
        return runInterval;
    }

    public void setRunInterval(String runInterval) {
        this.runInterval = runInterval;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public String getRunEnvDesc() {
        return runEnvDesc;
    }

    public void setRunEnvDesc(String runEnvDesc) {
        this.runEnvDesc = runEnvDesc;
    }
}
