package com.treefinance.saas.management.console.web.filter;

import com.datatrees.toolkits.util.http.servlet.ServletRequestUtils;
import com.treefinance.saas.management.console.common.domain.Constants;
import com.treefinance.saas.management.console.common.domain.dto.AuthUserDTO;
import com.treefinance.saas.management.console.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by haojiahong on 2017/9/5.
 */
public class LoggingRequestFilter extends AbstractRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestFilter.class);

    @Override
    protected void initFilterBean(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String ip = null;
        try {
            ip = ServletRequestUtils.getIP(request);
        } catch (Exception e) {
            logger.error("获取ip出错:url={},method={}", request.getRequestURI(), request.getMethod());
        }
        AuthUserDTO user = (AuthUserDTO) request.getSession().getAttribute(Constants.USER_KEY);

        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long time = System.currentTimeMillis() - start;
            logger.info("===>url={},user={},method={},time={},ip={},耗时={}ms",
                    request.getRequestURI(), user == null ? "用户未登录" : user.getLoginName(), request.getMethod(), DateUtils.date2Hms(new Date()), ip, time);
        }
    }

}
