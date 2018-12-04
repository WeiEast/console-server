package com.treefinance.saas.console.common.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengtong
 * @date 18/4/28 11:10
 *
 * 商户流量的配置列表返回
 *
 */
public class MerchantFlowAllotVO implements Serializable{

    private Long id;

    private String appId;

    private String type;

    private String appName;

    private List<MerchantFlowEnvQuotaVO> quotaVOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<MerchantFlowEnvQuotaVO> getQuotaVOList() {
        return quotaVOList;
    }

    public void setQuotaVOList(List<MerchantFlowEnvQuotaVO> quotaVOList) {
        this.quotaVOList = quotaVOList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
