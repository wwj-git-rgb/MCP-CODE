package com.wwj.domain.core.annotation;

import java.lang.annotation.*;

/**
 * 标记一个类为聚合根
 *
 * @author wenjie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AggregateRoot {
}