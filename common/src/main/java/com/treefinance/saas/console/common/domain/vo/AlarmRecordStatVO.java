package com.treefinance.saas.console.common.domain.vo;

/**
 * @author chengtong
 * @date 18/8/13 20:15
 */
public class AlarmRecordStatVO extends BaseVO {

    /**
     * 预警名称
     * */
    private String name;

    /**
     * 次数
     * */
    private Integer count;

    /**
     * 时长
     * */
    private Double duration;

    /**
     * 平均时长
     * */
    private Double durationAver;

    /**
     * 单次最大时长
     * */
    private Double maxDuration;

    /**
     * 已处理次数
     * */
    private Integer processedCount;

    /**
     * 误报次数
     * */
    private Integer wrongCount;

    /**
     * 无法解决次数
     * */
    private Integer disableCount;

    /**
     * 系统恢复次数
     * */
    private Integer recoveryCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDurationAver() {
        return durationAver;
    }

    public void setDurationAver(Double durationAver) {
        this.durationAver = durationAver;
    }

    public Double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Integer getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(Integer processedCount) {
        this.processedCount = processedCount;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public Integer getDisableCount() {
        return disableCount;
    }

    public void setDisableCount(Integer disableCount) {
        this.disableCount = disableCount;
    }

    public Integer getRecoveryCount() {
        return recoveryCount;
    }

    public void setRecoveryCount(Integer recoveryCount) {
        this.recoveryCount = recoveryCount;
    }
}
