package com.treefinance.saas.console.web.filter;

import com.treefinance.saas.console.common.domain.dto.AuthUserInfoDTO;
import com.treefinance.saas.console.context.Constants;
import com.treefinance.saas.console.util.DateUtils;
import com.treefinance.toolkit.util.http.servlet.ServletRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

/**
 * Created by haojiahong on 2017/9/5.
 */
public class LoggingRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String ip = null;
        try {
            ip = ServletRequests.getIP(request);
        } catch (Exception e) {
            logger.error("获取ip出错:url={},method={}", request.getRequestURI(), request.getMethod());
        }
        AuthUserInfoDTO user = (AuthUserInfoDTO) request.getSession().getAttribute(Constants.USER_KEY);
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
