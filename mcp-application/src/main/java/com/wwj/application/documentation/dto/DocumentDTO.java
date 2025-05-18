package com.wwj.application.documentation.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 文档数据传输对象
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Data
public class DocumentDTO implements Serializable {

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
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 文档状态（1: 草稿, 2: 已发布, 3: 已归档）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;
}
