package com.treefinance.saas.console.manager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jerry
 * @date 2018/11/29 16:47
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MerchantDailyAccessStatResultSet implements Serializable {

    private List<MerchantDailyAccessStatBO> data;
    private long total;

}
