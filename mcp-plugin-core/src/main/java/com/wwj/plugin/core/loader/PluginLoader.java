package com.wwj.plugin.core.loader;

import com.wwj.plugin.api.annotation.MCP;
import com.wwj.plugin.core.properties.MCPPluginProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 插件加载器
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
public class PluginLoader {

    private final MCPPluginProperties properties;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private final Map<String, Object> loadedPlugins = new HashMap<>();

    public PluginLoader(MCPPluginProperties properties) {
        this.properties = properties;
    }

    /**
     * 初始化插件
     */
    @PostConstruct
    public void init() {
        log.info("开始扫描并加载插件");
        scanPlugins();
    }

    /**
     * 扫描并加载插件
     */
    private void scanPlugins() {
        // 获取所有标记为MCP注解的Bean
        Map<String, Object> mcpBeans = applicationContext.getBeansWithAnnotation(MCP.class);
        
        for (Map.Entry<String, Object> entry : mcpBeans.entrySet()) {
            Object bean = entry.getValue();
            MCP mcp = bean.getClass().getAnnotation(MCP.class);
            String pluginName = mcp.name();
            
            // 检查插件是否已启用
            if (isPluginEnabled(pluginName)) {
                log.info("加载插件: {}({})", pluginName, mcp.version());
                loadedPlugins.put(pluginName, bean);
            } else {
                log.info("插件 {} 已禁用，跳过加载", pluginName);
            }
        }
        
        log.info("共加载了 {} 个插件", loadedPlugins.size());
    }

    /**
     * 检查插件是否启用
     *
     * @param pluginName 插件名称
     * @return 是否启用
     */
    private boolean isPluginEnabled(String pluginName) {
        // 首先检查所有插件一级开关
        if (!properties.isEnabled()) {
            return false;
        }
        
        // 然后检查具体插件配置
        for (MCPPluginProperties.PluginConfig config : properties.getPlugins()) {
            if (pluginName.equals(config.getName())) {
                return config.isEnabled();
            }
        }
        
        // 默认启用
        return true;
    }

    /**
     * 获取已加载的插件
     *
     * @param pluginName 插件名称
     * @param <T>        插件类型
     * @return 插件实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getPlugin(String pluginName) {
        return (T) loadedPlugins.get(pluginName);
    }

    /**
     * 获取所有已加载的插件
     *
     * @return 插件映射
     */
    public Map<String, Object> getAllPlugins() {
        return new HashMap<>(loadedPlugins);
    }
}