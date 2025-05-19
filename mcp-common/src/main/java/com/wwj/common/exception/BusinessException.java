package com.wwj.common.exception;

import com.wwj.common.result.IErrorCode;

/**
 * 业务异常
 *
 * @author wenjie
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    public BusinessException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}