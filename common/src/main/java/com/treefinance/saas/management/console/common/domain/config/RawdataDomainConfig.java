package com.treefinance.saas.management.console.common.domain.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/3/20
 */
public class RawdataDomainConfig implements Serializable {
    private static final long serialVersionUID = 4542454236476711379L;
    /**
     * 域名
     */
    private String domian;

    /**
     * 系统标签
     */
    private String systemSymbol;

    /**
     * 匹配路径
     */
    private String patternPath;

    /**
     * 需要被域名替换的路径
     */
    private String removePath;

    public String getDomian() {
        return domian;
    }

    public void setDomian(String domian) {
        this.domian = domian;
    }

    public String getSystemSymbol() {
        return systemSymbol;
    }

    public void setSystemSymbol(String systemSymbol) {
        this.systemSymbol = systemSymbol;
    }

    public String getPatternPath() {
        return patternPath;
    }

    public void setPatternPath(String patternPath) {
        this.patternPath = patternPath;
    }

    public String getRemovePath() {
        return removePath;
    }

    public void setRemovePath(String removePath) {
        this.removePath = removePath;
    }

    public static void main(String[] args) {
        List<RawdataDomainConfig> list = Lists.newArrayList();
        RawdataDomainConfig config1 = new RawdataDomainConfig();
        config1.setPatternPath("/saas/console/rawdata/wiseproxy/**");
        config1.setDomian("http://wiseproxy.saas.test.treefinance.com.cn/");
        config1.setSystemSymbol("wiseproxy");
        config1.setRemovePath("/saas/console/rawdata/");

        RawdataDomainConfig config2 = new RawdataDomainConfig();
        config2.setPatternPath("/saas/console/rawdata/crawler_monitor/**");
        config2.setDomian("http://192.168.5.25:7789/");
        config2.setSystemSymbol("crawler_monitor");
        config2.setRemovePath("/saas/console/rawdata/crawler_monitor");

        RawdataDomainConfig config3 = new RawdataDomainConfig();
        config3.setPatternPath("/saas/console/rawdata/rawdatacentral/**");
        config3.setDomian("http://192.168.5.25:6789/");
        config3.setSystemSymbol("rawdatacentral");
        config3.setRemovePath("/saas/console/rawdata/rawdatacentral");

        list.add(config1);
        list.add(config2);
        list.add(config3);
        System.out.println(JSON.toJSONString(list));

    }
}
