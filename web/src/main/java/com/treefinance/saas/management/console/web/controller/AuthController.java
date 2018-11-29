package com.treefinance.saas.management.console.web.controller;

import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import com.treefinance.saas.console.context.Constants;
import com.treefinance.saas.console.context.annotations.RequestLimit;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.knife.result.SaasResult;
import com.treefinance.saas.management.console.common.domain.dto.AuthUserInfoDTO;
import com.treefinance.saas.management.console.common.domain.vo.LoginVO;
import com.treefinance.saas.management.console.common.domain.vo.PwdCryptVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by haojiahong on 2017/6/23.
 */
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final String PWD_KEY = "console-server";

    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;

    @RequestMapping(value = "/logout", method = {RequestMethod.POST}, produces = "application/json")
    public SaasResult<?> logout() {
        Subject subject = SecurityUtils.getSubject();
        logger.info("用户退出：", subject.getPrincipal());
        subject.logout();
        return Results.newSuccessResult("退出成功!");
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<?> login() {
        logger.info("用户未登录");
        return Results.newFailedResult(CommonStateCode.NOT_LOGGED_IN);
    }

    @RequestMapping(value = "/forbidden", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<?> forbidden() {
        logger.info("用户无权限");
        return Results.newFailedResult(CommonStateCode.NO_PERMISSION);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = "application/json", consumes = "application/json")
    @RequestLimit(counts = {5, 30, 40}, times = {1, 30, 1},
            timeUnits = {TimeUnit.SECONDS, TimeUnit.SECONDS, TimeUnit.MINUTES})
    public SaasResult<?> doLogin(@RequestBody LoginVO loginVO, HttpSession session)  {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        try {
            UsernamePasswordToken userToken = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            logger.info("对用户[{}]进行登录验证..验证开始", username);
            subject.login(userToken);
            logger.info("用户[{}]登录认证通过", username);
            //设置app请求的session超时时间为1个月
            if (loginVO.getSource() != null && loginVO.getSource() == 1) {
                subject.getSession().setTimeout(1000 * 60 * 60 * 24 * 30L);
            } else {
                subject.getSession().setTimeout(1000 * 60 * 60 * 12L);
            }
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
        }
    }

    @RequestMapping(value = "/currentuser", method = {RequestMethod.GET}, produces = "application/json")
    public SaasResult<?> getLoginUser(HttpSession session) {
        try {
            AuthUserInfoDTO authUserBO = (AuthUserInfoDTO) session.getAttribute(Constants.USER_KEY);
            return Results.newSuccessResult(authUserBO);
        } catch (Exception e) {
            logger.error("get currentuser error,", e);
            return Results.newFailedResult(CommonStateCode.FAILURE, "获取用户失败");
        }

    }


    @RequestMapping(value = "/pwd/encrypt", method = {RequestMethod.POST}, produces = "application/json")
    public SaasResult<String> encryptPwd(@RequestBody PwdCryptVO pwdCryptVO) {
        if (pwdCryptVO == null || StringUtils.isBlank(pwdCryptVO.getPwd())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        String text = iSecurityCryptoService.encrypt(pwdCryptVO.getPwd(), EncryptionIntensityEnum.NORMAL);
        return Results.newSuccessResult(text);
    }

    @RequestMapping(value = "/pwd/decrypt", method = {RequestMethod.POST}, produces = "application/json")
    public SaasResult<String> decryptPwd(@RequestBody PwdCryptVO pwdCryptVO) {
        if (pwdCryptVO == null || StringUtils.isBlank(pwdCryptVO.getPwd())
                || StringUtils.isBlank(pwdCryptVO.getKey()) || !PWD_KEY.equals(pwdCryptVO.getKey())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        String text = iSecurityCryptoService.decrypt(pwdCryptVO.getPwd(), EncryptionIntensityEnum.NORMAL);
        return Results.newSuccessResult(text);
    }

    @RequestMapping(value = "/pwd/batch/encrypt", method = {RequestMethod.POST}, produces = "application/json")
    public SaasResult<Map<String, String>> batchEncryptPwd(@RequestBody PwdCryptVO pwdCryptVO) {
        if (pwdCryptVO == null || CollectionUtils.isEmpty(pwdCryptVO.getPwds())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        Map<String, String> map = iSecurityCryptoService.batchEncrypt(pwdCryptVO.getPwds(), EncryptionIntensityEnum.NORMAL);
        return Results.newSuccessResult(map);
    }

    @RequestMapping(value = "/pwd/batch/decrypt", method = {RequestMethod.POST}, produces = "application/json")
    public SaasResult<Map<String, String>> batchDecryptPwd(@RequestBody PwdCryptVO pwdCryptVO) {
        if (pwdCryptVO == null || CollectionUtils.isEmpty(pwdCryptVO.getPwds())
                || StringUtils.isBlank(pwdCryptVO.getKey()) || !PWD_KEY.equals(pwdCryptVO.getKey())) {
            return Results.newFailedResult(CommonStateCode.PARAMETER_LACK);
        }
        Map<String, String> map = iSecurityCryptoService.batchDecrypt(pwdCryptVO.getPwds(), EncryptionIntensityEnum.NORMAL);
        return Results.newSuccessResult(map);
    }


}
