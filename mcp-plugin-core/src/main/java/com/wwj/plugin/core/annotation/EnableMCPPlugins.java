package com.wwj.plugin.core.annotation;

import com.wwj.plugin.core.config.MCPPluginAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用MCP插件支持
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MCPPluginAutoConfiguration.class)
public @interface EnableMCPPlugins {
}