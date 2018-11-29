package com.treefinance.saas.console.manager.query;

import java.io.Serializable;

/**
 * @author Jerry
 * @date 2018/12/1 23:18
 */
abstract class AbstractPaginationQuery implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNumber;
    /**
     * 每页显示数
     */
    private Integer pageSize;
}
