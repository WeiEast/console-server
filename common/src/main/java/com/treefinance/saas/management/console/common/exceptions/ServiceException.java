package com.treefinance.saas.management.console.common.exceptions;

/**
 * @author Jerry
 * @date 2018/11/15 15:58
 */
public class ServiceException extends UnexpectedException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
