package com.treefinance.saas.console.biz.common.config;

import java.io.Serializable;

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

}
