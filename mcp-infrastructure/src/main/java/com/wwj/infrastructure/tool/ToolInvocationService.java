package com.wwj.infrastructure.tool;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolRegistry;
import com.wwj.domain.core.tool.ToolRequest;
import com.wwj.domain.core.tool.ToolResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 工具调用服务
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ToolInvocationService {

    private final ToolRegistry toolRegistry;

    /**
     * 同步执行工具
     *
     * @param request 工具请求
     * @return 工具响应
     */
    public ToolResponse invoke(ToolRequest request) {
        String toolName = request.getName();
        log.debug("正在调用工具: {}, 参数: {}", toolName, request.getParameters());
        
        Tool tool = toolRegistry.getTool(toolName);
        if (tool == null) {
            log.error("工具不存在: {}", toolName);
            return ToolResponse.error("工具不存在: " + toolName);
        }
        
        try {
            ToolResponse response = tool.execute(request);
            log.debug("工具调用完成: {}, 成功: {}", toolName, response.isSuccess());
            return response;
        } catch (Exception e) {
            log.error("工具调用异常: " + toolName, e);
            return ToolResponse.error("工具执行异常: " + e.getMessage());
        }
    }

    /**
     * 异步执行工具
     *
     * @param request 工具请求
     * @return 异步工具响应
     */
    public CompletableFuture<ToolResponse> invokeAsync(ToolRequest request) {
        String toolName = request.getName();
        log.debug("正在异步调用工具: {}, 参数: {}", toolName, request.getParameters());
        
        Tool tool = toolRegistry.getTool(toolName);
        if (tool == null) {
            log.error("工具不存在: {}", toolName);
            return CompletableFuture.completedFuture(ToolResponse.error("工具不存在: " + toolName));
        }
        
        return tool.executeAsync(request)
                .exceptionally(e -> {
                    log.error("工具异步调用异常: " + toolName, e);
                    return ToolResponse.error("工具执行异常: " + e.getMessage());
                });
    }
}