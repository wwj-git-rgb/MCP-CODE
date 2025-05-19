package com.wwj.plugin.api.annotation;

import java.lang.annotation.*;

/**
 * 工具参数注解
 * <p>用于标记工具类的字段作为参数</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {
    
    /**
     * 参数名称
     */
    String name();
    
    /**
     * 参数描述
     */
    String description() default "";
    
    /**
     * 参数类型
     * <p>JSON Schema类型，如string, number, boolean, object, array等</p>
     */
    String type() default "";
    
    /**
     * 是否必需
     */
    boolean required() default false;
    
    /**
     * 默认值（JSON字符串表示）
     */
    String defaultValue() default "";
    
    /**
     * 枚举选项（JSON数组字符串表示）
     */
    String enumValues() default "";
}