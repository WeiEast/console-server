package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jerry
 * @date 2018/11/23 19:18
 */
@Getter
@Setter
@ToString
public class LicenseBO implements Serializable {

    private String appId;
    private Byte bizType;
    private Byte isShowLicense;
    private String licenseTemplate;
    private Byte isValid;
    private Integer dailyLimit;
    private BigDecimal trafficLimit;
    private Byte h5Access;
    private Byte sdkAccess;
    private Byte apiAccess;

}
