package com.wwj.common.constants;

/**
 * 统一响应状态码枚举
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public enum ResultStatus {

    /**
     * 成功
     */
    SUCCESS(0, "操作成功"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 方法不允许
     */
    METHOD_NOT_ALLOWED(405, "方法不允许"),

    /**
     * 业务错误
     */
    BUSINESS_ERROR(1000, "业务处理失败"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(5000, "系统内部错误"),

    /**
     * 外部服务错误
     */
    EXTERNAL_SERVICE_ERROR(5001, "外部服务调用失败"),

    /**
     * 数据库错误
     */
    DATABASE_ERROR(5002, "数据库操作失败"),

    /**
     * 缓存错误
     */
    CACHE_ERROR(5003, "缓存操作失败"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(9999, "未知错误");

    private final int code;
    private final String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
