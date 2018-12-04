package com.treefinance.saas.console.biz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 13:19
 */
@Getter
@Setter
@ToString
public class IdentifiedBizType implements Serializable {

    private Long id;

    private Byte bizType;

    private String bizName;

}
