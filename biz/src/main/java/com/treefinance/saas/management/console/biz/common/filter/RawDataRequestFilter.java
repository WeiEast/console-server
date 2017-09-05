package com.treefinance.saas.management.console.biz.common.filter;

import com.datatrees.toolkits.util.http.servlet.ServletResponseUtils;
import com.datatrees.toolkits.util.json.Jackson;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by haojiahong on 2017/9/5.
 */
public class RawDataRequestFilter extends OncePerRequestFilter {

    private static String CONSOLE_RAW_DATA_URL = "/saas/console/rawdata";

    private DiamondConfig diamondConfig;

    public RawDataRequestFilter(DiamondConfig diamondConfig) {
        this.diamondConfig = diamondConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            AntPathMatcher matcher = new AntPathMatcher();
            String patternPath = "/saas/console/rawdata/**";
            String requestPath = request.getRequestURI();
            String result = "";
            if (matcher.match(patternPath, requestPath)) {
                String rawDataUrl = diamondConfig.getDomainRawdataWiseproxy() + StringUtils.removeFirst(requestPath, CONSOLE_RAW_DATA_URL);
                String method = request.getMethod();
                if ("GET".equals(method)) {
                    Map<String, Object> paramMap = Maps.newHashMap();
                    Enumeration<String> paramNames = request.getParameterNames();
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        paramMap.put(paramName, request.getParameter(paramName));
                    }
                    result = HttpClientUtils.doGet(rawDataUrl, paramMap);
                }
                String responseBody = Jackson.toJSONString(Results.newSuccessResult(result));
                ServletResponseUtils.responseJson(response, HttpStatus.OK.value(), responseBody);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            responseException(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    private void responseException(HttpServletRequest request, Exception ex,
                                   HttpStatus httpStatus, HttpServletResponse response) {
        handleLog(request, ex);
        String responseBody = Jackson.toJSONString(Results.newFailedResult(CommonStateCode.FAILURE));
        ServletResponseUtils.responseJson(response, httpStatus.value(), responseBody);
    }

    private void handleLog(HttpServletRequest request, Exception ex) {
        StringBuffer logBuffer = new StringBuffer();
        if (request != null) {
            logBuffer.append("request method=" + request.getMethod());
            logBuffer.append(",url=" + request.getRequestURL());
        }
        if (ex != null) {
            logBuffer.append(",exception:" + ex);
        }
        logger.error(logBuffer.toString(), ex);
    }

}
