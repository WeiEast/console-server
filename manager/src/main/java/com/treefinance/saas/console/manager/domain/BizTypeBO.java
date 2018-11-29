package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 10:09
 */
@Getter
@Setter
@ToString
public class BizTypeBO implements Serializable {

    private Byte bizType;

    private String bizName;

    private String bizCode;

    private Integer timeout;
}
