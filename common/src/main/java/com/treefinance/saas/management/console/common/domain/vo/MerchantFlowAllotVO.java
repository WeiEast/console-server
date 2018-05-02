package com.treefinance.saas.management.console.common.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengtong
 * @date 18/4/28 11:10
 *
 * 商户流量的配置列表返回
 *
 */
@ApiModel()
public class MerchantFlowAllotVO implements Serializable{

    @ApiModelProperty(name = "编号")
    private Long id;

    @ApiModelProperty(name = "商户编号")
    private String appId;

    @ApiModelProperty(name = "商户类型")
    private String type;

    @ApiModelProperty(name = "商户名称")
    private String appName;

    @ApiModelProperty(name = "环境和对应的流量配置数据的列表")
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
