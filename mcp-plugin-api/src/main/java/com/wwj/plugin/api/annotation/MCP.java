package com.wwj.plugin.api.annotation;

import java.lang.annotation.*;

/**
 * 标记一个类为MCP插件
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MCP {
    /**
     * 插件名称
     */
    String name();
    
    /**
     * 插件版本
     */
    String version();
    
    /**
     * 插件描述
     */
    String description() default "";
    
    /**
     * 支持的MCP平台最低版本
     */
    String mcpVersion() default "0.0.1";
    
    /**
     * 插件作者
     */
    String author() default "";
    
    /**
     * 插件依赖
     */
    MCPDependency[] dependencies() default {};
}