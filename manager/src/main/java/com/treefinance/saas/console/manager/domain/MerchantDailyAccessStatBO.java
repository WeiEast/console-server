package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/11/29 16:47
 */
@Getter
@Setter
@ToString
public class MerchantDailyAccessStatBO implements Serializable {

    private String appId;

    private Byte saasEnv;
    private Byte dataType;
    private Date dataTime;

    private Integer userCount;
    private Integer totalCount;
    private Integer successCount;
    private Integer failCount;
    private Integer cancelCount;
    private BigDecimal successRate;
    private BigDecimal failRate;

    private Integer dailyLimit;
}
