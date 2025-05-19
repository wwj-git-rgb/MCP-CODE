package com.wwj.plugin.core.lifecycle;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 插件管理器
 * <p>管理插件的生命周期和工具注册</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
public class PluginManager implements InitializingBean, DisposableBean {

    private final ApplicationContext applicationContext;
    private final ToolRegistry toolRegistry;
    private final List<PluginLifecycle> pluginLifecycles = new ArrayList<>();

    public PluginManager(ApplicationContext applicationContext, ToolRegistry toolRegistry) {
        this.applicationContext = applicationContext;
        this.toolRegistry = toolRegistry;
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化插件生命周期
        Map<String, PluginLifecycle> lifecycleBeans = applicationContext.getBeansOfType(PluginLifecycle.class);
        lifecycleBeans.forEach((name, lifecycle) -> {
            log.info("发现插件: {}", name);
            pluginLifecycles.add(lifecycle);
            lifecycle.initialize();
        });
        
        // 注册工具
        Map<String, Tool> toolBeans = applicationContext.getBeansOfType(Tool.class);
        toolBeans.forEach((name, tool) -> {
            log.info("发现工具: {}, 描述: {}", tool.getName(), tool.getDescription());
            toolRegistry.registerTool(tool);
        });
        
        // 启动插件
        pluginLifecycles.forEach(lifecycle -> {
            try {
                lifecycle.start();
                log.info("插件启动: {}", lifecycle.getClass().getName());
            } catch (Exception e) {
                log.error("插件启动失败: " + lifecycle.getClass().getName(), e);
            }
        });
    }

    @Override
    public void destroy() {
        // 停止并销毁插件
        for (int i = pluginLifecycles.size() - 1; i >= 0; i--) {
            PluginLifecycle lifecycle = pluginLifecycles.get(i);
            try {
                lifecycle.stop();
                lifecycle.destroy();
                log.info("插件停止和销毁: {}", lifecycle.getClass().getName());
            } catch (Exception e) {
                log.error("插件关闭失败: " + lifecycle.getClass().getName(), e);
            }
        }
    }
}