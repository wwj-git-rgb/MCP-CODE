package com.wwj.test.tool;

import com.wwj.domain.core.tool.Tool;
import com.wwj.domain.core.tool.ToolParameter;
import com.wwj.domain.core.tool.ToolRequest;
import com.wwj.domain.core.tool.ToolResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * 模拟工具
 * <p>用于测试的工具实现，可自定义行为</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
public class MockTool implements Tool {

    private final String name;
    private final String description;
    private final List<ToolParameter> parameters;
    private final Function<ToolRequest, ToolResponse> handler;

    private MockTool(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.parameters = builder.parameters;
        this.handler = builder.handler;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<ToolParameter> getParameters() {
        return parameters;
    }

    @Override
    public ToolResponse execute(ToolRequest request) {
        return handler.apply(request);
    }

    @Override
    public CompletableFuture<ToolResponse> executeAsync(ToolRequest request) {
        return CompletableFuture.supplyAsync(() -> handler.apply(request));
    }

    /**
     * 创建模拟工具构建器
     *
     * @param name 工具名称
     * @return 构建器
     */
    public static Builder builder(String name) {
        return new Builder(name);
    }

    /**
     * 模拟工具构建器
     */
    public static class Builder {
        private final String name;
        private String description = "";
        private final List<ToolParameter> parameters = new ArrayList<>();
        private Function<ToolRequest, ToolResponse> handler = request -> ToolResponse.success("Default mock response");

        public Builder(String name) {
            this.name = name;
        }

        /**
         * 设置工具描述
         *
         * @param description 描述
         * @return 构建器
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * 添加参数
         *
         * @param parameter 参数
         * @return 构建器
         */
        public Builder addParameter(ToolParameter parameter) {
            this.parameters.add(parameter);
            return this;
        }

        /**
         * 添加参数
         *
         * @param name 参数名称
         * @param description 参数描述
         * @param type 参数类型
         * @param required 是否必需
         * @return 构建器
         */
        public Builder addParameter(String name, String description, String type, boolean required) {
            this.parameters.add(ToolParameter.builder()
                    .name(name)
                    .description(description)
                    .type(type)
                    .required(required)
                    .build());
            return this;
        }

        /**
         * 设置处理函数
         *
         * @param handler 处理函数
         * @return 构建器
         */
        public Builder handler(Function<ToolRequest, ToolResponse> handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 设置成功响应
         *
         * @param content 响应内容
         * @return 构建器
         */
        public Builder successResponse(Object content) {
            this.handler = request -> ToolResponse.success(content);
            return this;
        }

        /**
         * 设置错误响应
         *
         * @param errorMessage 错误消息
         * @return 构建器
         */
        public Builder errorResponse(String errorMessage) {
            this.handler = request -> ToolResponse.error(errorMessage);
            return this;
        }

        /**
         * 基于参数返回不同响应
         *
         * @param parameterResponses 参数和响应映射
         * @return 构建器
         */
        public Builder parameterBasedResponses(Map<String, Object> parameterResponses) {
            this.handler = request -> {
                String key = String.valueOf(request.getParameters().get("key"));
                Object response = parameterResponses.getOrDefault(key, "Default response");
                return ToolResponse.success(response);
            };
            return this;
        }

        /**
         * 构建模拟工具
         *
         * @return 模拟工具实例
         */
        public MockTool build() {
            return new MockTool(this);
        }
    }
}