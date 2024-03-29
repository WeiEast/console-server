/**
 * Copyright © 2017 Treefinance All Rights Reserved
 */
package com.treefinance.saas.console.web.shiro;

import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.toolkit.util.http.servlet.ServletResponses;
import com.treefinance.toolkit.util.json.Jackson;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 重写权限过滤器禁止访问方法,防止默认返回到login.jsp页面
 * Created by chenjh on 2017/6/20.
 * <p>
 */
public class FormAuthenticationExtFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(FormAuthenticationExtFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            String responseBody = Jackson.toJSONString(Results.newFailedResult(CommonStateCode.NOT_LOGGED_IN));
            ServletResponses.responseJson((HttpServletResponse) response, HttpStatus.OK.value(), responseBody);
            return false;
        }
    }
}
