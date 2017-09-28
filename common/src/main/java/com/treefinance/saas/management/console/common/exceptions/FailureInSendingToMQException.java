package com.treefinance.saas.management.console.common.exceptions;

/**
 * @author luoyihua
 * @Title: FailureInSendingToMQException.java
 * @Description:
 * @date 2017年5月8日 下午2:00:43
 */

public class FailureInSendingToMQException extends Exception {

    /**  */
    private static final long serialVersionUID = -919211389588748211L;

    public FailureInSendingToMQException(String message) {
        super(message);
    }

    public FailureInSendingToMQException(String message, Throwable cause) {
        super(message, cause);
    }
}
