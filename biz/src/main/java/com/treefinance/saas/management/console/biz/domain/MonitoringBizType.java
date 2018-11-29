package com.treefinance.saas.management.console.biz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 14:56
 */
@Getter
@Setter
@ToString
public class MonitoringBizType implements Serializable {

    private String name;

    private Byte bizType;

}
