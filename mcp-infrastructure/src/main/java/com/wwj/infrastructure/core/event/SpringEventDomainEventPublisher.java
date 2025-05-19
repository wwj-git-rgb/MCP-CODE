package com.wwj.infrastructure.core.event;

import com.wwj.domain.core.event.DomainEvent;
import com.wwj.domain.core.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 基于Spring事件的领域事件发布器
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringEventDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        log.debug("发布领域事件: {}", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }
}