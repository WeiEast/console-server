package com.treefinance.saas.management.console.common.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengtong
 * @date 18/4/28 11:17
 *
 * 商户流量配置 环境流量mapping
 *
 */
public class MerchantFlowEnvQuotaVO implements Serializable{

    private BigDecimal quota;

    private String envName;

    private String envDesc;

    public String getEnvDesc() {
        return envDesc;
    }

    public void setEnvDesc(String envDesc) {
        this.envDesc = envDesc;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
}
