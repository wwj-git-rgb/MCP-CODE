package com.wwj.plugin.api.annotation;

import java.lang.annotation.*;

/**
 * MCP工具注解
 * <p>用于标记实现Tool接口的工具类</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MCPTool {
    
    /**
     * 工具名称
     */
    String name();
    
    /**
     * 工具描述
     */
    String description() default "";
    
    /**
     * 工具版本
     */
    String version() default "1.0.0";
    
    /**
     * 是否启用
     */
    boolean enabled() default true;
}