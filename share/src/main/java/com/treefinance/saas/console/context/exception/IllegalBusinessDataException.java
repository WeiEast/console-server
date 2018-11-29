package com.treefinance.saas.console.context.exception;

/**
 * @author Jerry
 * @date 2018/11/27 14:27
 */
public class IllegalBusinessDataException extends UnexpectedException {

    public IllegalBusinessDataException(String message) {
        super(message);
    }

    public IllegalBusinessDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBusinessDataException(Throwable cause) {
        super(cause);
    }
}
