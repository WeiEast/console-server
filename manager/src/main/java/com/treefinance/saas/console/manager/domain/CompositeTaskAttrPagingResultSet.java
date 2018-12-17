package com.treefinance.saas.console.manager.domain;

import java.util.List;

/**
 * @author Jerry
 * @date 2018/12/17 09:55
 */
public class CompositeTaskAttrPagingResultSet extends PagingResultSet<CompositeTaskAttrBO> {

    public CompositeTaskAttrPagingResultSet() {}

    public CompositeTaskAttrPagingResultSet(List<CompositeTaskAttrBO> list, Long total) {
        super(list, total);
    }
}
