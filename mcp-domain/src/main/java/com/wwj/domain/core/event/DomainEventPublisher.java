package com.wwj.domain.core.event;

/**
 * 领域事件发布者接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface DomainEventPublisher {

    /**
     * 发布领域事件
     *
     * @param event 领域事件
     */
    void publish(DomainEvent event);

    /**
     * 异步发布领域事件
     *
     * @param event 领域事件
     */
    void publishAsync(DomainEvent event);
}
