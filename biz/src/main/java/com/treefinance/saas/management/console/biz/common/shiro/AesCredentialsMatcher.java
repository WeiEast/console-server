package com.treefinance.saas.management.console.biz.common.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by haojiahong on 2017/7/4.
 */
public class AesCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        String password = String.valueOf(authcToken.getPassword());
        Object accountCredentials = getCredentials(info);
        String encryptText = EncryptHelper.encrypt(password);
        return equals(encryptText, accountCredentials);
    }
}
