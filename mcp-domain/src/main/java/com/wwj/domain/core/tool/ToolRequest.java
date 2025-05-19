package com.wwj.domain.core.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 工具请求
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolRequest {
    
    /**
     * 工具名称
     */
    private String name;
    
    /**
     * 请求参数
     */
    private Map<String, Object> parameters;
    
    /**
     * 上下文ID（可用于关联多个请求）
     */
    private String contextId;
}