package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/6/27.
 */
public class LoginVO implements Serializable {


    private static final long serialVersionUID = -1529325350407435747L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;

    private String password;

    private Byte source;

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }
}
