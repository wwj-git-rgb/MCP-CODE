package com.wwj.application.documentation.service;

import com.wwj.application.documentation.dto.DocumentDTO;

import java.util.List;

/**
 * 文档应用服务接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface DocumentService {
    /**
     * 创建或更新文档
     *
     * @param documentDTO 文档DTO
     * @return 保存后的文档DTO
     */
    DocumentDTO saveDocument(DocumentDTO documentDTO);

    /**
     * 根据ID获取文档
     *
     * @param id 文档ID
     * @return 文档DTO
     */
    DocumentDTO getDocumentById(String id);

    /**
     * 根据标题查找文档
     *
     * @param title 标题
     * @return 文档DTO列表
     */
    List<DocumentDTO> findDocumentsByTitle(String title);

    /**
     * 搜索文档
     *
     * @param keyword 关键词
     * @return 文档DTO列表
     */
    List<DocumentDTO> searchDocuments(String keyword);

    /**
     * 删除文档
     *
     * @param id 文档ID
     * @return 是否删除成功
     */
    boolean deleteDocument(String id);
}