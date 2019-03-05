package com.treefinance.saas.console.common.domain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张琰佳
 * @since 4:52 PM 2019/3/5
 */
@Setter
@Getter
public class MerchantFunctionVO {
    private Long id;

    private String appId;

    private String appName;

    private Integer sync;

    private String syncUrl;
}
