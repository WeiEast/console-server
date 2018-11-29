package com.treefinance.saas.console.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jerry
 * @date 2018/12/1 23:38
 */
@Getter
@Setter
@ToString
public class TaskLogBO implements Serializable {

    private Long id;

    private Long taskId;

    private String stepCode;

    private String msg;

    private Date occurTime;

    private String errorMsg;
}
