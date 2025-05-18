package com.wwj.domain.core.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 领域服务注解
 * 用于标记领域服务实现类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface DomainService {

    /**
     * 服务名称
     */
    String value() default "";
}
