package com.treefinance.saas.management.console.common.exceptions;


/**
 * Created by haojiahong on 2017/6/29.
 */

public class RequestLimitException extends RuntimeException {

    private static final long serialVersionUID = -7029578125901272069L;

    public RequestLimitException(String message) {
        super(message);
    }

    public RequestLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
