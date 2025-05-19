package com.wwj.domain.core.tool;

import java.util.Collection;

/**
 * 工具注册表
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface ToolRegistry {
    
    /**
     * 注册工具
     *
     * @param tool 工具实现
     */
    void registerTool(Tool tool);
    
    /**
     * 移除工具
     *
     * @param toolName 工具名称
     * @return 移除的工具，如果不存在则返回null
     */
    Tool removeTool(String toolName);
    
    /**
     * 获取工具
     *
     * @param name 工具名称
     * @return 工具实现，不存在则返回null
     */
    Tool getTool(String name);
    
    /**
     * 获取所有已注册的工具
     *
     * @return 工具集合
     */
    Collection<Tool> getAllTools();
    
    /**
     * 检查工具是否存在
     *
     * @param name 工具名称
     * @return 是否存在
     */
    boolean hasTool(String name);
}