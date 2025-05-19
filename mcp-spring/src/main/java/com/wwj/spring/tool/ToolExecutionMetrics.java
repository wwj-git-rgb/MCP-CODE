package com.wwj.spring.tool;

import com.wwj.domain.core.tool.ToolRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 工具执行监控
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
public class ToolExecutionMetrics implements InitializingBean, DisposableBean {

    private final ToolRegistry toolRegistry;
    private final Map<String, AtomicLong> invocationCounts = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> successCounts = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> failureCounts = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> totalDurations = new ConcurrentHashMap<>();

    public ToolExecutionMetrics(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    @Override
    public void afterPropertiesSet() {
        // 初始化监控指标
        toolRegistry.getAllTools().forEach(tool -> {
            String toolName = tool.getName();
            invocationCounts.put(toolName, new AtomicLong(0));
            successCounts.put(toolName, new AtomicLong(0));
            failureCounts.put(toolName, new AtomicLong(0));
            totalDurations.put(toolName, new AtomicLong(0));
        });
    }

    /**
     * 记录工具调用
     *
     * @param toolName  工具名称
     * @param success   是否成功
     * @param durationMs 执行时间（毫秒）
     */
    public void recordInvocation(String toolName, boolean success, long durationMs) {
        invocationCounts.computeIfAbsent(toolName, k -> new AtomicLong(0)).incrementAndGet();
        
        if (success) {
            successCounts.computeIfAbsent(toolName, k -> new AtomicLong(0)).incrementAndGet();
        } else {
            failureCounts.computeIfAbsent(toolName, k -> new AtomicLong(0)).incrementAndGet();
        }
        
        totalDurations.computeIfAbsent(toolName, k -> new AtomicLong(0)).addAndGet(durationMs);
    }

    /**
     * 获取工具调用次数
     *
     * @param toolName 工具名称
     * @return 调用次数
     */
    public long getInvocationCount(String toolName) {
        AtomicLong count = invocationCounts.get(toolName);
        return count != null ? count.get() : 0;
    }

    /**
     * 获取工具调用成功次数
     *
     * @param toolName 工具名称
     * @return 成功次数
     */
    public long getSuccessCount(String toolName) {
        AtomicLong count = successCounts.get(toolName);
        return count != null ? count.get() : 0;
    }

    /**
     * 获取工具调用失败次数
     *
     * @param toolName 工具名称
     * @return 失败次数
     */
    public long getFailureCount(String toolName) {
        AtomicLong count = failureCounts.get(toolName);
        return count != null ? count.get() : 0;
    }

    /**
     * 获取工具平均执行时间（毫秒）
     *
     * @param toolName 工具名称
     * @return 平均执行时间
     */
    public double getAverageDuration(String toolName) {
        AtomicLong total = totalDurations.get(toolName);
        AtomicLong count = invocationCounts.get(toolName);
        
        if (total == null || count == null || count.get() == 0) {
            return 0;
        }
        
        return (double) total.get() / count.get();
    }

    @Override
    public void destroy() {
        // 销毁时打印统计信息
        log.info("工具调用统计信息:");
        invocationCounts.forEach((toolName, count) -> {
            log.info("工具: {}, 调用次数: {}, 成功: {}, 失败: {}, 平均执行时间: {}毫秒",
                    toolName, count.get(), getSuccessCount(toolName), getFailureCount(toolName), getAverageDuration(toolName));
        });
    }
}