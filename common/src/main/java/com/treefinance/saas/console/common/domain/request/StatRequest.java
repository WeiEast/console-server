package com.treefinance.saas.console.common.domain.request;

import com.treefinance.saas.knife.request.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by haojiahong on 2017/7/5.
 */
public class StatRequest extends PageRequest {

    /**
     * 0-自选日期，1-过去1天，2-过去3天，3-过去7天，4-过去30天
     */
    private Integer dateType = 0;

    /**
     * 自选开始日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 自选结束日期 (yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 排序规则：1-成功率升序，2-成功率降序，4-访问次数升序，5-访问次数降序
     */
    private Integer collationType;

    /**
     * 商户任务总览:1-成功率;2-失败率;3-取消率
     * 任务失败取消环节统计:1-失败率;2-取消率
     */
    private Integer statType;

    private String appId;

    /**
     * 数据类型：-1-合计，-2:银行，2：电商，1:邮箱，3:运营商 EBizType4Monitor.class
     */
    private Byte bizType;

    /**
     * 数据显示的时间间隔(默认10分钟)
     */
    private Integer intervalMins = 10;

    /**
     *
     */
    private Byte saasEnv;

    /**
     * 曲线图是否隐藏最近一个点的数据
     */
    private Byte hiddenRecentPoint = 1;


    public Byte getHiddenRecentPoint() {
        return hiddenRecentPoint;
    }

    public void setHiddenRecentPoint(Byte hiddenRecentPoint) {
        this.hiddenRecentPoint = hiddenRecentPoint;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCollationType() {
        return collationType;
    }

    public void setCollationType(Integer collationType) {
        this.collationType = collationType;
    }

    public Integer getStatType() {
        return statType;
    }

    public void setStatType(Integer statType) {
        this.statType = statType;
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(Byte bizType) {
        this.bizType = bizType;
    }

    public Integer getIntervalMins() {
        return intervalMins;
    }

    public void setIntervalMins(Integer intervalMins) {
        this.intervalMins = intervalMins;
    }

    public Byte getSaasEnv() {
        return saasEnv;
    }

    public void setSaasEnv(Byte saasEnv) {
        this.saasEnv = saasEnv;
    }
}
