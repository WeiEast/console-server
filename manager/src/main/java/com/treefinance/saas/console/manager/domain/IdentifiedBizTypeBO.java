package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 11:40
 */
@Getter
@Setter
@ToString
public class IdentifiedBizTypeBO implements Serializable {

    private Long id;

    private Byte bizType;

    private String bizName;

}
