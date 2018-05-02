package com.treefinance.saas.management.console.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chengtong
 * @date 18/4/28 11:17
 *
 * 商户流量配置 环境流量mapping
 *
 */
@ApiModel
public class MerchantFlowEnvQuotaVO implements Serializable{

    @ApiModelProperty(name = "流量配置")
    private BigDecimal quota;

    @ApiModelProperty(name = "环境名称",example = "dev|test")
    private String envName;

    @ApiModelProperty(name = "环境名称",example = "开发|测试")
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
