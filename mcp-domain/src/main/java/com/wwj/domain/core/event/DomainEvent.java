package com.wwj.domain.core.event;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 领域事件基类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Getter
public abstract class DomainEvent {
    /**
     * 事件发生时间
     */
    private final LocalDateTime occurredTime;
    
    /**
     * 构造函数
     */
    public DomainEvent() {
        this.occurredTime = LocalDateTime.now();
    }
}