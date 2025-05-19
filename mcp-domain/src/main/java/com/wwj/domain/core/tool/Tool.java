package com.wwj.domain.core.tool;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * MCP工具接口
 * <p>参考Model Context Protocol规范实现</p>
 * 
 * @author wenjie
 * @since 1.0.0
 */
public interface Tool {
    /**
     * 获取工具名称
     * 
     * @return 工具名称
     */
    String getName();
    
    /**
     * 获取工具描述
     * 
     * @return 工具描述
     */
    String getDescription();
    
    /**
     * 获取工具参数定义
     * 
     * @return 参数列表
     */
    List<ToolParameter> getParameters();
    
    /**
     * 执行工具（同步）
     * 
     * @param request 工具请求
     * @return 工具响应
     */
    ToolResponse execute(ToolRequest request);
    
    /**
     * 执行工具（异步）
     * 
     * @param request 工具请求
     * @return 异步工具响应
     */
    default CompletableFuture<ToolResponse> executeAsync(ToolRequest request) {
        return CompletableFuture.supplyAsync(() -> execute(request));
    }
}