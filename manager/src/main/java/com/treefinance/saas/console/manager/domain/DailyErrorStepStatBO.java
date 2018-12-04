package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/12/1 23:25
 */
@Getter
@Setter
@ToString
public class DailyErrorStepStatBO implements Serializable {

    private Date dataTime;

    private Byte dataType;

    private String errorStepCode;

    private Integer failCount;
}
