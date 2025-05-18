package com.wwj.common.exception;

import com.wwj.common.constants.ResultStatus;

/**
 * 业务异常类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public class BusinessException extends BaseException {
    
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public BusinessException() {
        super(ResultStatus.BUSINESS_ERROR);
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     */
    public BusinessException(String message) {
        super(ResultStatus.BUSINESS_ERROR.getCode(), message);
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     */
    public BusinessException(int code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     * @param cause   原因
     */
    public BusinessException(String message, Throwable cause) {
        super(ResultStatus.BUSINESS_ERROR.getCode(), message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     * @param cause   原因
     */
    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     */
    public BusinessException(ResultStatus resultStatus) {
        super(resultStatus);
    }

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     * @param cause      原因
     */
    public BusinessException(ResultStatus resultStatus, Throwable cause) {
        super(resultStatus, cause);
    }
}
