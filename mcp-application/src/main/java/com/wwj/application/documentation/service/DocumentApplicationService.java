package com.wwj.application.documentation.service;

import com.wwj.application.documentation.dto.DocumentDTO;

import java.util.List;

/**
 * 文档应用服务接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface DocumentApplicationService {

    /**
     * 创建文档
     *
     * @param documentDTO 文档DTO
     * @return 创建后的文档DTO
     */
    DocumentDTO createDocument(DocumentDTO documentDTO);

    /**
     * 根据ID获取文档
     *
     * @param id 文档ID
     * @return 文档DTO
     */
    DocumentDTO getDocumentById(String id);

    /**
     * 更新文档
     *
     * @param documentDTO 文档DTO
     * @return 更新后的文档DTO
     */
    DocumentDTO updateDocument(DocumentDTO documentDTO);

    /**
     * 删除文档
     *
     * @param id 文档ID
     */
    void deleteDocument(String id);

    /**
     * 发布文档
     *
     * @param id 文档ID
     * @return 发布后的文档DTO
     */
    DocumentDTO publishDocument(String id);

    /**
     * 归档文档
     *
     * @param id 文档ID
     * @return 归档后的文档DTO
     */
    DocumentDTO archiveDocument(String id);

    /**
     * 搜索文档
     *
     * @param keyword 关键词
     * @return 文档DTO列表
     */
    List<DocumentDTO> searchDocuments(String keyword);

    /**
     * 获取所有文档
     *
     * @return 文档DTO列表
     */
    List<DocumentDTO> getAllDocuments();

    /**
     * 获取已发布的文档
     *
     * @return 文档DTO列表
     */
    List<DocumentDTO> getPublishedDocuments();
}
