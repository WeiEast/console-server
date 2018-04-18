package com.treefinance.saas.management.console.common.domain.dto;

/**
 * Buddha Bless , No Bug !
 *
 * @author haojiahong
 * @date 2018/4/17
 */
public class HttpResponseResult {

    private String responseBody;
    private Integer statusCode;

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
