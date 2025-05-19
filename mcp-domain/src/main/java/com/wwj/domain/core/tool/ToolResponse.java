package com.wwj.domain.core.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具响应
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResponse {
    
    /**
     * 是否成功
     */
    private boolean success;
    
    /**
     * 响应内容
     */
    private Object content;
    
    /**
     * 错误信息（如果失败）
     */
    private String errorMessage;
    
    /**
     * 创建成功响应
     *
     * @param content 响应内容
     * @return 响应对象
     */
    public static ToolResponse success(Object content) {
        return ToolResponse.builder()
                .success(true)
                .content(content)
                .build();
    }
    
    /**
     * 创建失败响应
     *
     * @param errorMessage 错误信息
     * @return 响应对象
     */
    public static ToolResponse error(String errorMessage) {
        return ToolResponse.builder()
                .success(false)
                .errorMessage(errorMessage)
                .build();
    }
}