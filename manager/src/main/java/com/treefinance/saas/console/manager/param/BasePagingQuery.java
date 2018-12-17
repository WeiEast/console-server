package com.treefinance.saas.console.manager.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/12/1 23:18
 */
@Getter
@Setter
abstract class BasePagingQuery implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 每页显示数
     */
    private Integer pageSize;
}
