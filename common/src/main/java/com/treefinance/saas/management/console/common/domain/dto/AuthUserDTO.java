package com.treefinance.saas.management.console.common.domain.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户登录信息
 * Created by haojiahong on 2017/6/23.
 */
public class AuthUserDTO implements Serializable {

    private static final long serialVersionUID = -312234886346061013L;
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
    private String jSessionId;

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

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }
}
