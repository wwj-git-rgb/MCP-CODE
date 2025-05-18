package com.wwj.common.exception;

import com.wwj.common.constants.ResultStatus;

/**
 * 系统异常类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public SystemException() {
        super(ResultStatus.SYSTEM_ERROR);
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     */
    public SystemException(String message) {
        super(ResultStatus.SYSTEM_ERROR.getCode(), message);
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     */
    public SystemException(int code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     * @param cause   原因
     */
    public SystemException(String message, Throwable cause) {
        super(ResultStatus.SYSTEM_ERROR.getCode(), message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     * @param cause   原因
     */
    public SystemException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     */
    public SystemException(ResultStatus resultStatus) {
        super(resultStatus);
    }

    /**
     * 构造函数
     * 
     * @param resultStatus 状态枚举
     * @param cause      原因
     */
    public SystemException(ResultStatus resultStatus, Throwable cause) {
        super(resultStatus, cause);
    }
}
