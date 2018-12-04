package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/7/26.
 */
public class AppCallbackUrlVO implements Serializable {

    private static final long serialVersionUID = 6965884676132995839L;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
