package com.treefinance.saas.management.console.common.domain.vo.rawdata;

import java.io.Serializable;

/**
 * Created by haojiahong on 2017/8/30.
 */
public class ProxyRuleVO implements Serializable {
    private static final long serialVersionUID = 6589455374325276775L;

    private Byte maxFailure;  // 最大失败数
    private Short limitUsed;  // 每次定时检查时代理的最大使用数
    private Short maxUsed;  // 最大使用数
    private Short expiration; // 过期时间，单位：分钟

    public Byte getMaxFailure() {
        return maxFailure;
    }

    public void setMaxFailure(Byte maxFailure) {
        this.maxFailure = maxFailure;
    }

    public Short getLimitUsed() {
        return limitUsed;
    }

    public void setLimitUsed(Short limitUsed) {
        this.limitUsed = limitUsed;
    }

    public Short getMaxUsed() {
        return maxUsed;
    }

    public void setMaxUsed(Short maxUsed) {
        this.maxUsed = maxUsed;
    }

    public Short getExpiration() {
        return expiration;
    }

    public void setExpiration(Short expiration) {
        this.expiration = expiration;
    }
}
