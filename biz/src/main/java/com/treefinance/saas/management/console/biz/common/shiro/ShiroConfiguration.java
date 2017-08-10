/**
 * Copyright © 2017 Treefinance All Rights Reserved
 */
package com.treefinance.saas.management.console.biz.common.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chenjh on 2017/6/12.
 * <p>
 * shiro配置类
 */
@Configuration
public class ShiroConfiguration {

    private static final int GLOBALSESSIONTIMEOU = 12 * 60 * 60 * 1000;

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 这个类是为了对密码进行编码的， 防止密码在数据库里明码保存，当然在登陆认证的时候，
     */
    @Bean(name = "consoleCredentialsMatcher")
    public AesCredentialsMatcher consoleCredentialsMatcher() {
        return new AesCredentialsMatcher();
    }

    @Bean(name = "base64CredentialsMatcher")
    public Base64CredentialsMatcher base64CredentialsMatcher() {
        return new Base64CredentialsMatcher();
    }

    @Bean(name = "baseServiceCredentialsMatcher")
    public BaseServiceCredentialsMatcher baseServiceCredentialsMatcher() {
        return new BaseServiceCredentialsMatcher();
    }


    /**
     * 设置自定义realm
     *
     * @return
     */
    @Bean(name = "userRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(baseServiceCredentialsMatcher());
        return realm;
    }

    /**
     * 设置共享session
     *
     * @return
     */
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        return new RedisSessionDAO();
    }

    /**
     * 设置redisCacheManager
     *
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setGlobalSessionTimeout(GLOBALSESSIONTIMEOU);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //LogoutFilter logoutFilter = new LogoutFilter();
        //logoutFilter.setRedirectUrl("/login");
        //shiroFilterFactoryBean.getFilters().put("logout", logoutFilter);
        FormAuthenticationExtFilter authcFilter = new FormAuthenticationExtFilter();
        shiroFilterFactoryBean.getFilters().put("authc", authcFilter);
        // 权限配置
        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
        //filterChainDefinitionManager.put("/logout", "logout");
        filterChainDefinitionManager.put("/saas/console/**", "authc");
        filterChainDefinitionManager.put("/currentuser", "authc");
        filterChainDefinitionManager.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
        //shiroFilterFactoryBean.setSuccessUrl("/");
        //.setUnauthorizedUrl("/forbidden");
        //shiroFilterFactoryBean.setLoginUrl("/login");
        //System.out.println(shiroFilterFactoryBean.getFilters());
        return shiroFilterFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }

}
