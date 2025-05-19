package com.wwj.plugin.core.lifecycle;

/**
 * 插件生命周期接口
 * <p>定义插件生命周期的各个阶段</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface PluginLifecycle {
    
    /**
     * 初始化插件
     * <p>在Spring容器启动时调用，用于准备插件运行环境</p>
     */
    default void initialize() {
        // 默认为空实现
    }
    
    /**
     * 启动插件
     * <p>在初始化完成后调用，用于启动插件功能</p>
     */
    default void start() {
        // 默认为空实现
    }
    
    /**
     * 停止插件
     * <p>在应用关闭或插件卸载前调用，用于停止插件功能</p>
     */
    default void stop() {
        // 默认为空实现
    }
    
    /**
     * 销毁插件
     * <p>在插件卸载或应用关闭时调用，用于释放插件资源</p>
     */
    default void destroy() {
        // 默认为空实现
    }
}