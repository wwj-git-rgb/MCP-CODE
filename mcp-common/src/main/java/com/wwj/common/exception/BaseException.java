package com.wwj.common.exception;

import com.wwj.common.constants.ResultStatus;

/**
 * 基础异常类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     */
    public BaseException(ResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    /**
     * 构造函数
     * 
     * @param code    错误码
     * @param message 错误消息
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param code    错误码
     * @param message 错误消息
     * @param cause   原因
     */
    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     * @param cause      原因
     */
    public BaseException(ResultStatus resultStatus, Throwable cause) {
        super(resultStatus.getMessage(), cause);
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
