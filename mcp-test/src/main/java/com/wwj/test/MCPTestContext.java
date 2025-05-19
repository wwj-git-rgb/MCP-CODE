package com.wwj.test;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolRequest;
import com.wwj.domain.core.tool.ToolResponse;
import com.wwj.test.tool.MockTool;
import com.wwj.test.tool.MockToolRegistry;

import java.util.Map;

/**
 * MCP测试上下文
 * <p>提供测试环境和工具调用函数</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
public class MCPTestContext {

    private final MockToolRegistry toolRegistry = new MockToolRegistry();

    /**
     * 注册工具
     *
     * @param tool 工具实例
     * @return 工具实例
     */
    public Tool registerTool(Tool tool) {
        toolRegistry.registerTool(tool);
        return tool;
    }

    /**
     * 注册模拟工具
     *
     * @param mockTool 模拟工具
     * @return 模拟工具
     */
    public MockTool registerMockTool(MockTool mockTool) {
        toolRegistry.registerTool(mockTool);
        return mockTool;
    }

    /**
     * 创建并注册模拟工具
     *
     * @param name 工具名称
     * @return 模拟工具构建器
     */
    public MockTool.Builder createMockTool(String name) {
        return MockTool.builder(name);
    }

    /**
     * 调用工具
     *
     * @param name 工具名称
     * @param parameters 工具参数
     * @return 工具响应
     */
    public ToolResponse invokeTool(String name, Map<String, Object> parameters) {
        Tool tool = toolRegistry.getTool(name);
        if (tool == null) {
            throw new IllegalArgumentException("工具不存在: " + name);
        }
        
        ToolRequest request = ToolRequest.builder()
                .name(name)
                .parameters(parameters)
                .build();
        
        return tool.execute(request);
    }

    /**
     * 获取工具
     *
     * @param name 工具名称
     * @return 工具实例
     */
    public Tool getTool(String name) {
        return toolRegistry.getTool(name);
    }

    /**
     * 移除工具
     *
     * @param name 工具名称
     * @return 被移除的工具
     */
    public Tool removeTool(String name) {
        return toolRegistry.removeTool(name);
    }

    /**
     * 清空所有工具
     */
    public void clearTools() {
        toolRegistry.clearAll();
    }

    /**
     * 获取工具注册表
     *
     * @return 工具注册表
     */
    public MockToolRegistry getToolRegistry() {
        return toolRegistry;
    }
}