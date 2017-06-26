package com.treefinance.saas.management.console.common.result;

/**
 * Created by haojiahong on 2017/6/26.
 */
public class PageRequest {
    /**
     * 当前页
     */
    private int pageNumber = 1;
    /**
     * 每页显示数
     */
    private int pageSize = 20;
    /**
     * 偏移量
     */
    private int offset = 0;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        offset = (pageNumber - 1) * pageSize;
        return offset;
    }
}
