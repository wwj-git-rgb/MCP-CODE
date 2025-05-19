package com.wwj.application.documentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 文档DTO
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    /**
     * 文档ID
     */
    private String id;

    /**
     * 标题
     */
    @NotBlank(message = "文档标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "文档内容不能为空")
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