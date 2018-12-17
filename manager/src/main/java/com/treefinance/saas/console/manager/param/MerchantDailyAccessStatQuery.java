package com.treefinance.saas.console.manager.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/11/29 16:34
 */
@Getter
@Setter
@ToString
public class MerchantDailyAccessStatQuery implements Serializable {

    private String appId;
    /**
     * 数据类型：-1-合计，-2:银行，2：电商，1:邮箱，3:运营商
     */
    private Byte dataType;
    private Date startDate;
    private Date endDate;
    /**
     * 数据显示的时间间隔(默认10分钟)
     */
    private Integer intervalMinutes;
    /**
     * 环境标识
     */
    private Byte saasEnv;

    /**
     * 当前页
     */
    private Integer pageNumber;
    /**
     * 每页显示数
     */
    private Integer pageSize;
}
