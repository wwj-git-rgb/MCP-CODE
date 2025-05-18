package com.wwj.plugin.api.weather;

import java.lang.annotation.*;

/**
 * MCP工具注解
 * 标记类为MCP工具，可被MCP系统调用
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MCPTool {
    
    /**
     * 工具名称
     */
    String name() default "";
    
    /**
     * 工具描述
     */
    String description() default "";
    
    /**
     * 工具版本
     */
    String version() default "1.0.0";
    
    /**
     * 工具图标
     */
    String icon() default "";
    
    /**
     * 工具类别
     */
    String category() default "";
    
    /**
     * 是否启用
     */
    boolean enabled() default true;
}