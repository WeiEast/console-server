package com.treefinance.saas.management.console.common.domain.vo;

import java.math.BigDecimal;

/**
 * Created by haojiahong on 2017/7/5.
 */
public class MerchantStatDayVO extends MerchantStatVO {

    private static final long serialVersionUID = 6733383226678787694L;

    public BigDecimal getUseOnTotalLimitRate() {
        return useOnTotalLimitRate;
    }

    public void setUseOnTotalLimitRate(BigDecimal useOnTotalLimitRate) {
        this.useOnTotalLimitRate = useOnTotalLimitRate;
    }

    private BigDecimal useOnTotalLimitRate;//使用配额占总配额比例
}
