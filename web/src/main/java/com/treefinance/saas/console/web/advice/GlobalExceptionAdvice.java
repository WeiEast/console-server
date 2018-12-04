/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.treefinance.saas.console.web.advice;

import com.treefinance.saas.console.context.ConsoleStateCode;
import com.treefinance.saas.console.context.exception.BizException;
import com.treefinance.saas.console.context.exception.RequestLimitException;
import com.treefinance.saas.knife.common.CommonStateCode;
import com.treefinance.saas.knife.common.StateCode;
import com.treefinance.saas.knife.result.Results;
import com.treefinance.toolkit.util.http.servlet.ServletResponses;
import com.treefinance.toolkit.util.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * @author <A HREF="mailto:yaojun@datatrees.com.cn">Jun Yao</A>
 * @version 1.0
 * @since 2017年3月06日 上午10:12:41
 */
@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleValidationException(ValidationException ex,
                                          HttpServletRequest request, HttpServletResponse response) {
        responseException(request, CommonStateCode.FAILURE, ex, HttpStatus.BAD_REQUEST, response);
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleBizException(BizException ex,
                                   HttpServletRequest request, HttpServletResponse response) {
        responseException(request, CommonStateCode.FAILURE, ex, HttpStatus.BAD_REQUEST, response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleIllegalArgumentException(IllegalArgumentException ex,
                                               HttpServletRequest request, HttpServletResponse response) {
        responseException(request, CommonStateCode.FAILURE, ex, HttpStatus.BAD_REQUEST, response);
    }

    //现将此异常作为警告信息
    @ExceptionHandler(RequestLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public void handleRequestLimitException(RequestLimitException ex, HttpServletRequest request, HttpServletResponse response) {
        responseException(request, ConsoleStateCode.REPEAT_REQUEST_ERROR, ex, HttpStatus.BAD_REQUEST, response);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public void handleAllException(HttpServletRequest request, Exception ex, HttpServletResponse response) {
        responseSystemException(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        HttpServletRequest request = null;
        if (webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
            request = servletRequest.getNativeRequest(HttpServletRequest.class);
        }
        handleLog(request, ex);
        return super.handleExceptionInternal(ex, body, headers, status, webRequest);
    }

    private void handleLog(HttpServletRequest request, Exception ex) {
        StringBuilder logBuffer = new StringBuilder();
        if (request != null) {
            logBuffer.append("request method=").append(request.getMethod());
            logBuffer.append(",url=").append(request.getRequestURL());
        }
        if (ex != null) {
            logBuffer.append(",exception:").append(ex.getMessage());
        }
        logger.error(logBuffer.toString(), ex);
    }

    private void responseException(HttpServletRequest request, StateCode stateCode, Exception ex,
                                   HttpStatus httpStatus, HttpServletResponse response) {
        handleLog(request, ex);
        String responseBody = Jackson.toJSONString(Results.newFailedResult(stateCode, ex.getMessage()));
        ServletResponses.responseJson(response, httpStatus.value(), responseBody);
    }

    /**
     * 友好处理未知异常信息
     *
     * @param request
     * @param ex
     * @param httpStatus
     * @param response
     */
    private void responseSystemException(HttpServletRequest request, Exception ex,
                                         HttpStatus httpStatus, HttpServletResponse response) {
        handleLog(request, ex);
        String responseBody = Jackson.toJSONString(Results.newFailedResult(CommonStateCode.FAILURE));
        ServletResponses.responseJson(response, httpStatus.value(), responseBody);
    }
}
