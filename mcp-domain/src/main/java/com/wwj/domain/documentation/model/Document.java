package com.wwj.domain.documentation.model;

import com.wwj.domain.core.annotation.AggregateRoot;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文档模型
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@AggregateRoot
public class Document {
    /**
     * 文档ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 文档类型
     */
    private String type;
    
    /**
     * 版本
     */
    private Integer version;
}