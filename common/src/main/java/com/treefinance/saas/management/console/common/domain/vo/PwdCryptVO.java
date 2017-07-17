package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by haojiahong on 2017/7/17.
 */
public class PwdCryptVO implements Serializable {
    private static final long serialVersionUID = 8775766698254573662L;

    private String pwd;

    private List<String> pwds;

    private String key;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<String> getPwds() {
        return pwds;
    }

    public void setPwds(List<String> pwds) {
        this.pwds = pwds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
