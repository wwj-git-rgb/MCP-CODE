package com.wwj.domain.core.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具参数定义
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolParameter {
    
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 参数描述
     */
    private String description;
    
    /**
     * 参数类型
     */
    private String type;
    
    /**
     * 是否必需
     */
    private boolean required;
    
    /**
     * 默认值
     */
    private Object defaultValue;
    
    /**
     * 枚举值（可选，限定值范围）
     */
    private Object[] enumValues;
}