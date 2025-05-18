package com.wwj.infrastructure.core.event;

import com.wwj.domain.core.event.DomainEvent;
import com.wwj.domain.core.event.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 领域事件发布者实现
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(DomainEventPublisherImpl.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        logger.debug("发布领域事件: {}, ID: {}", event.getEventType(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    @Override
    public void publishAsync(DomainEvent event) {
        logger.debug("异步发布领域事件: {}, ID: {}", event.getEventType(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }
}
