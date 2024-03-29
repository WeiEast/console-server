package com.treefinance.saas.console.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/11/27 13:30
 */
@Getter
@Setter
@ToString
public class AppBizTypeNameValueVO implements Serializable {

    private Long id;

    private Byte bizType;

    private String bizName;
}
