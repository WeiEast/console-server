package com.treefinance.saas.management.console.biz.common.shiro.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 *
 */
public abstract class AbstractRequestFilter extends OncePerRequestFilter {

    @Override
    protected void initFilterBean() throws ServletException {
        FilterConfig filterConfig = super.getFilterConfig();
        if (filterConfig != null) {
            initFilterBean(filterConfig);
        }
    }

    protected abstract void initFilterBean(FilterConfig filterConfig) throws ServletException;

}
