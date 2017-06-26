package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.common.domain.Constants;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.web.auth.exception.ForbiddenException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by haojiahong on 2017/6/23.
 */
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/logout", method = {RequestMethod.POST}, produces = "application/json")
    public Result<?> logout() {
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户退出：", subject.getPrincipal());
        subject.logout();
        Result<String> result = new Result<>();
        result.setData("退出成功!");
        return result;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> login() throws ForbiddenException {
        logger.info("用户未登录");
        return new Result<>("用户未登录!");
    }

    @RequestMapping(value = "/forbidden", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> forbidden() throws ForbiddenException {
        logger.info("用户无权限");
        return new Result<>("用户无权限!");
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = "application/json")
    public Result<?> doLogin(String username, String password, HttpSession session) throws ForbiddenException {
        String loginName = username;
        UsernamePasswordToken userToken = new UsernamePasswordToken(loginName, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            logger.info("对用户[{}]进行登录验证..验证开始", loginName);
            subject.login(userToken);
            logger.info("用户[{}]登录认证通过", loginName);
            return new Result<>(session.getAttribute(Constants.USER_KEY));
        } catch (UnknownAccountException e) {
            logger.warn(String.format("账号不存在，account=%s", loginName), e.getMessage());
            return new Result<>("账号不存在");
        } catch (LockedAccountException e) {
            logger.warn(String.format("账号未激活，account=%s", loginName), e.getMessage());
            return new Result<>("账号未激活");
        } catch (AuthenticationException e) {
            logger.warn(String.format("对用户[%s]进行登录验证..验证未通过", loginName), e);
            return new Result<>("验证未通过");
        } catch (Exception e) {
            logger.error(String.format("登陆失败，username=%s", username), e);
            return new Result<>("登陆失败");
        }
    }

    @RequestMapping(value = "/currentuser", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> getLoginUser() {
        Subject currentUser = SecurityUtils.getSubject();
        Object user = currentUser.getPrincipal();
        if (user == null) {
            return new Result<>("未查询到当前登录用户");
        }
        return new Result<>(user);

    }
}
