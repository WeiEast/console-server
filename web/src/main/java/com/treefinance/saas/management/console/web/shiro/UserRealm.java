package com.treefinance.saas.management.console.web.shiro;

import com.treefinance.saas.console.context.Constants;
import com.treefinance.saas.management.console.biz.service.ConsoleUserService;
import com.treefinance.saas.management.console.common.domain.dto.AuthUserInfoDTO;
import com.treefinance.saas.management.console.dao.entity.ConsoleUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luoyihua
 * @date 2017年5月18日 上午8:00:38
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ConsoleUserService consoleUserService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
//        authenticationInfo.setRoles(userService.findRoleNameByLoginName(username));
//        authenticationInfo.setStringPermissions(userService.findPermissionNameByLoginName(username));
        return authenticationInfo;
    }

    // 认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        ConsoleUser user = consoleUserService.getByLoginName(username);
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new LockedAccountException("账户未激活");
        }
        AuthUserInfoDTO authUser = new AuthUserInfoDTO();
        authUser.setLoginName(username);
        authUser.setActive(user.getIsActive());

//        Set<String> roles = userService.findRoleNameByLoginName(username);
//        userDTO.setRoles(roles);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        authUser.setSessionId(session.getId().toString());
        session.setAttribute(Constants.USER_KEY, authUser);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
