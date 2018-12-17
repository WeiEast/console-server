package com.treefinance.saas.console.manager.domain;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/17 09:55
 */
public class TaskPagingResultSet extends PagingResultSet<TaskBO> {

    public TaskPagingResultSet() {
    }

    public TaskPagingResultSet(List<TaskBO> list, Long total) {
        super(list, total);
    }
}
