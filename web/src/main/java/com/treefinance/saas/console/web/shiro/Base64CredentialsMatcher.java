package com.treefinance.saas.console.web.shiro;

import com.treefinance.toolkit.util.Base64Codec;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by haojiahong on 2017/7/4.
 */
public class Base64CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        String password = String.valueOf(authcToken.getPassword());
        Object accountCredentials = getCredentials(info);
        String encryptText = Base64Codec.encode(password.getBytes());
        return equals(encryptText, accountCredentials);
    }
}
