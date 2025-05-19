package com.wwj.test.tool;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟工具注册表
 * <p>用于测试工具注册和调用</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
public class MockToolRegistry implements ToolRegistry {

    private final Map<String, Tool> tools = new ConcurrentHashMap<>();

    @Override
    public void registerTool(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    @Override
    public Tool removeTool(String toolName) {
        return tools.remove(toolName);
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

    /**
     * 清空所有注册的工具
     */
    public void clearAll() {
        tools.clear();
    }
}