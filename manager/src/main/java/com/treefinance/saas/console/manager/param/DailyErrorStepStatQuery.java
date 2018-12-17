package com.treefinance.saas.console.manager.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/12/1 23:16
 */
@Getter
@Setter
@ToString
public class DailyErrorStepStatQuery implements Serializable {
    /**
     * 数据类型：0:合计，1:银行，2：电商，3:邮箱，4:运营商
     */
    private Byte dataType;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;
}
