package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * User: guougoyun
 * Date: 2018/7/5
 */
public class AppCrawlerConfigParamVO implements Serializable {

    /**
     * 商户appid
     */
    private String                    appId;
    /**
     * 商户appName
     */
    private String                    appName;
    /**
     * 业务名称
     */
    private List<String>              projectNames;
    /**
     * 具体业务info
     */
    private List<CrawlerProjectParam> projectConfigInfos;

    public AppCrawlerConfigParamVO() {
    }

    public AppCrawlerConfigParamVO(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    public List<CrawlerProjectParam> getProjectConfigInfos() {
        return projectConfigInfos;
    }

    public void setProjectConfigInfos(List<CrawlerProjectParam> projectConfigInfos) {
        this.projectConfigInfos = projectConfigInfos;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
