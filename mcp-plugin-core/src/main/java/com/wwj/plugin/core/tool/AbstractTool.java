package com.wwj.plugin.core.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolParameter;
import com.wwj.domain.core.tool.ToolRequest;
import com.wwj.domain.core.tool.ToolResponse;
import com.wwj.plugin.api.annotation.MCPTool;
import com.wwj.plugin.api.annotation.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具抽象基类
 * <p>提供工具实现的基础功能</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractTool implements Tool {

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String getName() {
        MCPTool annotation = getClass().getAnnotation(MCPTool.class);
        if (annotation != null) {
            return annotation.name();
        }
        return getClass().getSimpleName();
    }

    @Override
    public String getDescription() {
        MCPTool annotation = getClass().getAnnotation(MCPTool.class);
        if (annotation != null) {
            return annotation.description();
        }
        return "";
    }

    @Override
    public List<ToolParameter> getParameters() {
        List<ToolParameter> parameters = new ArrayList<>();
        for (Field field : getClass().getDeclaredFields()) {
            Parameter param = field.getAnnotation(Parameter.class);
            if (param != null) {
                parameters.add(ToolParameter.builder()
                        .name(param.name())
                        .description(param.description())
                        .type(param.type().isEmpty() ? getParameterType(field.getType()) : param.type())
                        .required(param.required())
                        .defaultValue(param.defaultValue().isEmpty() ? null : parseJsonValue(param.defaultValue()))
                        .enumValues(param.enumValues().isEmpty() ? null : parseJsonArray(param.enumValues()))
                        .build());
            }
        }
        return parameters;
    }

    @Override
    public ToolResponse execute(ToolRequest request) {
        try {
            // 先将参数注入到工具实例中
            injectParameters(request.getParameters());
            
            // 执行工具具体逻辑
            return doExecute(request);
        } catch (Exception e) {
            log.error("工具执行异常: " + getName(), e);
            return ToolResponse.error(e.getMessage());
        }
    }

    /**
     * 具体工具实现逻辑
     *
     * @param request 工具请求
     * @return 工具响应
     */
    protected abstract ToolResponse doExecute(ToolRequest request);

    /**
     * 将请求参数注入到工具实例中
     *
     * @param parameters 请求参数
     * @throws Exception 参数注入异常
     */
    protected void injectParameters(Map<String, Object> parameters) throws Exception {
        for (Field field : getClass().getDeclaredFields()) {
            Parameter param = field.getAnnotation(Parameter.class);
            if (param != null && parameters.containsKey(param.name())) {
                field.setAccessible(true);
                Object value = convertValue(parameters.get(param.name()), field.getType());
                field.set(this, value);
            }
        }
    }

    /**
     * 获取参数类型的字符串表示
     *
     * @param type Java类型
     * @return 参数类型字符串
     */
    private String getParameterType(Class<?> type) {
        if (String.class.isAssignableFrom(type)) {
            return "string";
        } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
            return "boolean";
        } else if (Number.class.isAssignableFrom(type) || type.isPrimitive()) {
            return "number";
        } else if (List.class.isAssignableFrom(type) || type.isArray()) {
            return "array";
        } else {
            return "object";
        }
    }

    /**
     * 转换参数值为目标类型
     *
     * @param value 原始值
     * @param targetType 目标类型
     * @return 转换后的值
     */
    private Object convertValue(Object value, Class<?> targetType) {
        try {
            if (value == null) {
                return null;
            }
            
            if (targetType.isInstance(value)) {
                return value;
            }
            
            return objectMapper.convertValue(value, targetType);
        } catch (Exception e) {
            log.warn("参数类型转换失败: {} -> {}", value, targetType.getName(), e);
            return value;
        }
    }

    /**
     * 解析JSON值
     */
    private Object parseJsonValue(String json) {
        try {
            return objectMapper.readValue(json, Object.class);
        } catch (Exception e) {
            return json;
        }
    }

    /**
     * 解析JSON数组
     */
    private Object[] parseJsonArray(String json) {
        try {
            return objectMapper.readValue(json, Object[].class);
        } catch (Exception e) {
            return null;
        }
    }
}