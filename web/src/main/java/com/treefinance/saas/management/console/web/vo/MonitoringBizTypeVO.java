package com.treefinance.saas.management.console.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 14:58
 */
@Getter
@Setter
@ToString
public class MonitoringBizTypeVO implements Serializable {
    private Byte bizType;
    private String bizName;

}
