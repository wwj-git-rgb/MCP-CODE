package com.wwj.domain.core.event;

/**
 * 领域事件发布器
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface DomainEventPublisher {
    /**
     * 发布领域事件
     *
     * @param event 领域事件
     */
    void publish(DomainEvent event);
}