package com.wwj.plugin.api.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 标记一个类为MCP工具
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Tool {
    /**
     * 工具名称
     */
    String name();
    
    /**
     * 工具描述
     */
    String description() default "";
    
    /**
     * 工具、、作者
     */
    String author() default "";
    
    /**
     * 工具版本
     */
    String version() default "1.0.0";
    
    /**
     * 工具标签
     */
    String[] tags() default {};
    
    /**
     * 工具动作注解
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface Action {
        /**
         * 动作名称
         */
        String name();
        
        /**
         * 动作描述
         */
        String description() default "";
    }
}