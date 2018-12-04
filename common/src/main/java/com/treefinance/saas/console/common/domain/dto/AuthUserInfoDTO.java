package com.treefinance.saas.console.common.domain.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户登录信息
 * Created by haojiahong on 2017/6/23.
 */
public class AuthUserInfoDTO implements Serializable {

    private static final long serialVersionUID = -6700062790963893908L;
    /**
     * 登陆用户名
     */
    private String loginName;

    /**
     * 是否激活
     */
    private Boolean isActive;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 角色列表
     */
    private Set<String> roles;

    private Set<String> permissions;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
