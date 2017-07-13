package com.treefinance.saas.management.console.biz.common.shiro;

import com.treefinance.basicservice.security.crypto.facade.EncryptionIntensityEnum;
import com.treefinance.basicservice.security.crypto.facade.ISecurityCryptoService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by haojiahong on 2017/7/13.
 */
public class BaseServiceCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private ISecurityCryptoService iSecurityCryptoService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        String password = String.valueOf(authcToken.getPassword());
        Object accountCredentials = getCredentials(info);
        String encryptText = iSecurityCryptoService.encrypt(password, EncryptionIntensityEnum.NORMAL);
        return equals(encryptText, accountCredentials);
    }
}
