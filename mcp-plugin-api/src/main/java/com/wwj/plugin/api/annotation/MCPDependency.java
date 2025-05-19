package com.wwj.plugin.api.annotation;

import java.lang.annotation.*;

/**
 * 插件依赖注解
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MCPDependency {
    /**
     * 依赖的插件名称
     */
    String name();
    
    /**
     * 版本要求
     * 格式示例：
     * - "1.0.0": 精确匹配1.0.0版本
     * - ">=1.0.0": 1.0.0及以上版本
     * - ">=1.0.0,<2.0.0": 1.0.0及以上，但不包括2.0.0及以上版本
     */
    String version();
}