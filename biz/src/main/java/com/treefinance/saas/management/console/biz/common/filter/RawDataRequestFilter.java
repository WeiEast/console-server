package com.treefinance.saas.management.console.biz.common.filter;

import com.alibaba.fastjson.JSON;
import com.datatrees.toolkits.util.http.servlet.ServletResponseUtils;
import com.datatrees.toolkits.util.json.Jackson;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.saas.management.console.biz.common.config.DiamondConfig;
import com.treefinance.saas.management.console.common.domain.config.RawdataDomainConfig;
import com.treefinance.saas.management.console.common.result.CommonStateCode;
import com.treefinance.saas.management.console.common.result.Results;
import com.treefinance.saas.management.console.common.utils.HttpClientUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rawdataDomianConfigStr = diamondConfig.getRawdataDomianConfig();
        if (StringUtils.isBlank(rawdataDomianConfigStr)) {
            logger.info("爬数路径域名映射未配置");
            filterChain.doFilter(request, response);
        }
        List<RawdataDomainConfig> configList = JSON.parseArray(rawdataDomianConfigStr, RawdataDomainConfig.class);
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
            String result = "";
            if (StringUtils.isNotBlank(url)) {
                String method = request.getMethod();
                if ("GET".equals(method)) {
                    Map<String, Object> paramMap = Maps.newHashMap();
                    Enumeration<String> paramNames = request.getParameterNames();
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        paramMap.put(paramName, request.getParameter(paramName));
                    }
                    result = HttpClientUtils.doGet(url, paramMap);
                }
                if (StringUtils.equalsIgnoreCase("post", request.getMethod())) {
                    InputStream is = request.getInputStream();
                    String body = IOUtils.toString(is, "utf-8");
                    result = HttpClientUtils.doPost(url, body);
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

    public static void main(String[] args) {
        List<RawdataDomainConfig> list = Lists.newArrayList();
        RawdataDomainConfig config1 = new RawdataDomainConfig();
        config1.setPatternPath("/saas/console/rawdata/wiseproxy/**");
        config1.setDomian("http://wiseproxy.saas.test.treefinance.com.cn/");
        config1.setSystemSymbol("wiseproxy");
        config1.setRemovePath("/saas/console/rawdata/");

        RawdataDomainConfig config2 = new RawdataDomainConfig();
        config2.setPatternPath("/saas/console/rawdata/crawler_monitor/**");
        config2.setDomian("http://192.168.5.25:7789/");
        config2.setSystemSymbol("crawler_monitor");
        config2.setRemovePath("/saas/console/rawdata/crawler_monitor");

        RawdataDomainConfig config3 = new RawdataDomainConfig();
        config3.setPatternPath("/saas/console/rawdata/rawdatacentral/**");
        config3.setDomian("http://192.168.5.25:6789/");
        config3.setSystemSymbol("rawdatacentral");
        config3.setRemovePath("/saas/console/rawdata/rawdatacentral");

        list.add(config1);
        list.add(config2);
        list.add(config3);
        System.out.println(JSON.toJSONString(list));

    }

}
