package com.treefinance.saas.management.console.common.exceptions;

/**
 * Created by luoyihua on 2017/5/10.
 */
public class CallbackEncryptException extends CryptoException{

    public CallbackEncryptException(String message) {
        super(message);
    }

    public CallbackEncryptException(String message, Throwable cause) {
        super(message, cause);
    }
}
