package com.treefinance.saas.management.console.web.controller;

import com.treefinance.saas.management.console.common.cache.redis.MoreRedisTemplate;
import com.treefinance.saas.management.console.common.domain.Constants;
import com.treefinance.saas.management.console.common.domain.bo.AuthUserBO;
import com.treefinance.saas.management.console.common.domain.vo.LoginVO;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Result;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.RedisKeyUtils;
import com.treefinance.saas.management.console.web.auth.exception.ForbiddenException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private MoreRedisTemplate moreRedisTemplate;

    @RequestMapping(value = "/logout", method = {RequestMethod.POST}, produces = "application/json")
    public Result<?> logout() {
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户退出：", subject.getPrincipal());
        subject.logout();
        return Results.newSuccessResult("退出成功!");
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> login() throws ForbiddenException {
        logger.info("用户未登录");
        return Results.newFailedResult(CommonStateCode.NOT_LOGGED_IN);
    }

    @RequestMapping(value = "/forbidden", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> forbidden() throws ForbiddenException {
        logger.info("用户无权限");
        return Results.newFailedResult(CommonStateCode.NO_PERMISSION);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = "application/json", consumes = "application/json")
    public Result<?> doLogin(@RequestBody LoginVO loginVO, HttpSession session) throws ForbiddenException {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        //防止用户重复点击登录.
        String key = RedisKeyUtils.genRedisKey(String.format("%s:%s", "auth", username));
        Boolean lock = moreRedisTemplate.acquireLock(key, 10 * 60 * 1000);
        if (!lock) {
            logger.info("用户发起重复登录请求!");
            return Results.newFailedResult(CommonStateCode.REPEAT_LOGGED_IN);
        }
        try {
            UsernamePasswordToken userToken = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            logger.info("对用户[{}]进行登录验证..验证开始", username);
            subject.login(userToken);
            logger.info("用户[{}]登录认证通过", username);
            return Results.newSuccessResult(session.getAttribute(Constants.USER_KEY));
        } catch (UnknownAccountException e) {
            logger.warn(String.format("账号不存在，account=%s", username), e.getMessage());
            return Results.newFailedResult(CommonStateCode.ACCOUNT_DOES_NOT_EXIST);
        } catch (LockedAccountException e) {
            logger.warn(String.format("账号未激活，account=%s", username), e.getMessage());
            return Results.newFailedResult(CommonStateCode.ACCOUNT_NOT_ACTIVATED);
        } catch (AuthenticationException e) {
            logger.warn(String.format("对用户[%s]进行登录验证..验证未通过", username), e);
            return Results.newFailedResult(CommonStateCode.LOGIN_VERIFY_ERROR);
        } catch (Exception e) {
            logger.error(String.format("登陆失败，username=%s", username), e);
            return Results.newFailedResult(CommonStateCode.FAILURE);
        } finally {
            moreRedisTemplate.releaseLock(key);
        }
    }

    @RequestMapping(value = "/currentuser", method = {RequestMethod.GET}, produces = "application/json")
    public Result<?> getLoginUser(HttpSession session) {
        try {
            AuthUserBO authUserBO = (AuthUserBO) session.getAttribute(Constants.USER_KEY);
            return Results.newSuccessResult(authUserBO);
        } catch (Exception e) {
            logger.error("get currentuser error,", e);
            return Results.newFailedResult(CommonStateCode.FAILURE, "获取用户失败");
        }

    }
}
