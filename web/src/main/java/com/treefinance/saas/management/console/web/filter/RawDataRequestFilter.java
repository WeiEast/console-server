package com.treefinance.saas.management.console.web.filter;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.common.domain.config.RawdataDomainConfig;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import com.treefinance.toolkit.util.http.servlet.ServletResponses;
import com.treefinance.toolkit.util.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by haojiahong on 2017/9/5.
 */
public class RawDataRequestFilter extends OncePerRequestFilter {

    private DiamondConfig diamondConfig;

    public RawDataRequestFilter(DiamondConfig diamondConfig) {
        this.diamondConfig = diamondConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<RawdataDomainConfig> configList = diamondConfig.getRawDataDomainConfigList();
        if (CollectionUtils.isEmpty(configList)) {
            logger.info("爬数路径域名映射未配置");
            filterChain.doFilter(request, response);
        }
        Map<String, RawdataDomainConfig> map = configList.stream()
                .collect(Collectors.toMap(RawdataDomainConfig::getPatternPath, rawdataDomainConfig -> rawdataDomainConfig));
        String url = "";
        AntPathMatcher matcher = new AntPathMatcher();
        String requestPath = request.getRequestURI();
        for (String patternPath : map.keySet()) {
            if (matcher.match(patternPath, requestPath)) {
                url = map.get(patternPath).getDomian() + StringUtils.removeFirst(requestPath, map.get(patternPath).getRemovePath());
            }
        }
        try {
            if (StringUtils.isNotBlank(url)) {
                String method = request.getMethod();
                if ("GET".equals(method)) {
                    HttpClientUtils.doGetForward(url, request, response);
                }
                if (StringUtils.equalsIgnoreCase("post", request.getMethod())) {
                    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                            request.getSession().getServletContext());

                    if (multipartResolver.isMultipart(request)) {
                        Collection<Part> parts = request.getParts();
                        InputStream in;
                        for (Part part : parts) {
                            //获取原始文件名
                            String fileName = part.getSubmittedFileName();
                            String fieldName = part.getName();
                            //获取文件流，可以进行处理
                            in = part.getInputStream();
                            HttpClientUtils.doPostMutiForward(url, request, response, fieldName, fileName, in);
                        }
                    } else {
                        HttpClientUtils.doPostForward(url, request, response);
                    }
                }
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            logger.error("转发器异常", ex);
            responseException(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    private void responseException(HttpServletRequest request, Exception ex,
                                   HttpStatus httpStatus, HttpServletResponse response) {
        handleLog(request, ex);
        String responseBody = Jackson.toJSONString(Results.newFailedResult(CommonStateCode.FAILURE));
        ServletResponses.responseJson(response, httpStatus.value(), responseBody);
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
