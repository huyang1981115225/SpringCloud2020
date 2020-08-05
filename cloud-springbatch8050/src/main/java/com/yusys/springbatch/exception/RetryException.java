package com.yusys.springbatch.exception;

/**
 * Created by huyang on 2019/10/12.
 */
public class RetryException extends Exception {

    public RetryException() {
        super();
    }

    public RetryException(String message) {
        super(message);
    }
}
