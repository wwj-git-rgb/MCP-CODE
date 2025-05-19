package com.wwj.infrastructure.tool;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单工具注册表实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
public class SimpleToolRegistry implements ToolRegistry {

    private final Map<String, Tool> tools = new ConcurrentHashMap<>();

    @Override
    public void registerTool(Tool tool) {
        String toolName = tool.getName();
        if (tools.containsKey(toolName)) {
            log.warn("工具 '{}' 已存在，将被覆盖", toolName);
        }
        tools.put(toolName, tool);
        log.info("已注册工具: {}, 描述: {}", toolName, tool.getDescription());
    }

    @Override
    public Tool removeTool(String toolName) {
        Tool removed = tools.remove(toolName);
        if (removed != null) {
            log.info("已移除工具: {}", toolName);
        }
        return removed;
    }

    @Override
    public Tool getTool(String name) {
        return tools.get(name);
    }

    @Override
    public Collection<Tool> getAllTools() {
        return Collections.unmodifiableCollection(tools.values());
    }

    @Override
    public boolean hasTool(String name) {
        return tools.containsKey(name);
    }
}