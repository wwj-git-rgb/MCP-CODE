package com.wwj.domain.documentation.service;

import com.wwj.domain.documentation.model.Document;

import java.util.List;

/**
 * 文档服务领域接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface DocumentDomainService {

    /**
     * 创建文档
     *
     * @param document 文档对象
     * @return 创建后的文档
     */
    Document createDocument(Document document);

    /**
     * 根据ID获取文档
     *
     * @param id 文档ID
     * @return 文档对象
     */
    Document getDocumentById(String id);

    /**
     * 更新文档
     *
     * @param document 文档对象
     * @return 更新后的文档
     */
    Document updateDocument(Document document);

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
     * @return 发布后的文档
     */
    Document publishDocument(String id);

    /**
     * 归档文档
     *
     * @param id 文档ID
     * @return 归档后的文档
     */
    Document archiveDocument(String id);

    /**
     * 根据标题搜索文档
     *
     * @param title 标题关键词
     * @return 文档列表
     */
    List<Document> searchByTitle(String title);

    /**
     * 根据内容搜索文档
     *
     * @param content 内容关键词
     * @return 文档列表
     */
    List<Document> searchByContent(String content);

    /**
     * 根据标签搜索文档
     *
     * @param tag 标签
     * @return 文档列表
     */
    List<Document> searchByTag(String tag);

    /**
     * 获取所有文档
     *
     * @return 文档列表
     */
    List<Document> getAllDocuments();

    /**
     * 获取所有已发布的文档
     *
     * @return 文档列表
     */
    List<Document> getPublishedDocuments();
}
