package com.treefinance.saas.management.console.biz.common.config;

import com.github.diamond.client.extend.annotation.AfterUpdate;
import com.github.diamond.client.extend.annotation.BeforeUpdate;
import com.github.diamond.client.extend.annotation.DAttribute;
import com.github.diamond.client.extend.annotation.DResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * super-diamond 配置
 */
@Component("diamondConfig")
@Scope
@DResource
public class DiamondConfig {
    private static final Logger logger = LoggerFactory.getLogger(DiamondConfig.class);

    @DAttribute(key = "appId.environment.prefix")
    private String appIdEnvironmentPrefix;

    @DAttribute(key = "domain.rawdata.wiseproxy")
    private String domainRawdataWiseproxy;

    public String getAppIdEnvironmentPrefix() {
        return appIdEnvironmentPrefix;
    }

    public void setAppIdEnvironmentPrefix(String appIdEnvironmentPrefix) {
        this.appIdEnvironmentPrefix = appIdEnvironmentPrefix;
    }

    public String getDomainRawdataWiseproxy() {
        return domainRawdataWiseproxy;
    }

    public void setDomainRawdataWiseproxy(String domainRawdataWiseproxy) {
        this.domainRawdataWiseproxy = domainRawdataWiseproxy;
    }

    @BeforeUpdate
    public void before(String key, Object newValue) {
        logger.info(key + " update to " + newValue + " start...");
    }

    @AfterUpdate
    public void after(String key, Object newValue) {
        logger.info(key + " update to " + newValue + " end...");
    }


}
