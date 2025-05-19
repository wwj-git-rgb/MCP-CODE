package com.wwj.domain.documentation.service;

import com.wwj.domain.documentation.model.Document;

import java.util.List;

/**
 * 文档领域服务接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface DocumentDomainService {
    /**
     * 创建或更新文档
     *
     * @param document 文档
     * @return 保存后的文档
     */
    Document saveDocument(Document document);

    /**
     * 根据ID查询文档
     *
     * @param id 文档ID
     * @return 文档
     */
    Document getDocumentById(String id);

    /**
     * 根据标题查询文档
     *
     * @param title 标题
     * @return 文档列表
     */
    List<Document> findDocumentsByTitle(String title);

    /**
     * 根据关键词搜索文档
     *
     * @param keyword 关键词
     * @return 文档列表
     */
    List<Document> searchDocuments(String keyword);

    /**
     * 删除文档
     *
     * @param id 文档ID
     * @return 是否删除成功
     */
    boolean deleteDocument(String id);
}