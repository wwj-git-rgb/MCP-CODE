package com.wwj.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wwj.common.constants.ResultStatus;

import java.io.Serializable;

/**
 * 统一响应结果封装
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 私有构造函数
     */
    private Result() {
    }

    /**
     * 构造函数
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数
     *
     * @param resultStatus 状态枚举
     * @param data        数据
     */
    private Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    /**
     * 成功结果
     *
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultStatus.SUCCESS, null);
    }

    /**
     * 成功结果（带数据）
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultStatus.SUCCESS, data);
    }

    /**
     * 成功结果（自定义消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(ResultStatus.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功结果（自定义消息和数据）
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return 结果对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultStatus.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败结果
     *
     * @param resultStatus 状态枚举
     * @param <T>         数据类型
     * @return 结果对象
     */
    public static <T> Result<T> failure(ResultStatus resultStatus) {
        return new Result<>(resultStatus, null);
    }

    /**
     * 失败结果（带数据）
     *
     * @param resultStatus 状态枚举
     * @param data        数据
     * @param <T>         数据类型
     * @return 结果对象
     */
    public static <T> Result<T> failure(ResultStatus resultStatus, T data) {
        return new Result<>(resultStatus, data);
    }

    /**
     * 失败结果（自定义消息）
     *
     * @param resultStatus 状态枚举
     * @param message     消息
     * @param <T>         数据类型
     * @return 结果对象
     */
    public static <T> Result<T> failure(ResultStatus resultStatus, String message) {
        return new Result<>(resultStatus.getCode(), message, null);
    }

    /**
     * 失败结果（自定义消息和数据）
     *
     * @param resultStatus 状态枚举
     * @param message     消息
     * @param data        数据
     * @param <T>         数据类型
     * @return 结果对象
     */
    public static <T> Result<T> failure(ResultStatus resultStatus, String message, T data) {
        return new Result<>(resultStatus.getCode(), message, data);
    }

    /**
     * 参数错误
     *
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> paramError() {
        return failure(ResultStatus.PARAM_ERROR);
    }

    /**
     * 参数错误（自定义消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果对象
     */
    public static <T> Result<T> paramError(String message) {
        return failure(ResultStatus.PARAM_ERROR, message);
    }

    /**
     * 系统错误
     *
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> systemError() {
        return failure(ResultStatus.SYSTEM_ERROR);
    }

    /**
     * 系统错误（自定义消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果对象
     */
    public static <T> Result<T> systemError(String message) {
        return failure(ResultStatus.SYSTEM_ERROR, message);
    }

    /**
     * 业务错误
     *
     * @param <T> 数据类型
     * @return 结果对象
     */
    public static <T> Result<T> businessError() {
        return failure(ResultStatus.BUSINESS_ERROR);
    }

    /**
     * 业务错误（自定义消息）
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return 结果对象
     */
    public static <T> Result<T> businessError(String message) {
        return failure(ResultStatus.BUSINESS_ERROR, message);
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return code == ResultStatus.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
