package com.wwj.domain.documentation.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文档领域模型
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Data
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    private String id;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 文档类型
     */
    private String type;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 标签列表（逗号分隔）
     */
    private String tags;

    /**
     * 文档状态（1: 草稿, 2: 已发布, 3: 已归档）
     */
    private Integer status;

    public Document() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = 1; // 默认为草稿状态
    }

    /**
     * 简单构造函数
     *
     * @param title   标题
     * @param content 内容
     */
    public Document(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    /**
     * 判断文档是否已发布
     *
     * @return 是否已发布
     */
    public boolean isPublished() {
        return status != null && status == 2;
    }

    /**
     * 判断文档是否已归档
     *
     * @return 是否已归档
     */
    public boolean isArchived() {
        return status != null && status == 3;
    }

    /**
     * 发布文档
     */
    public void publish() {
        this.status = 2;
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 归档文档
     */
    public void archive() {
        this.status = 3;
        this.updateTime = LocalDateTime.now();
    }
}
