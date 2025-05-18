package com.wwj.infrastructure.documentation.service;

import com.wwj.common.exception.BusinessException;
import com.wwj.domain.documentation.model.Document;
import com.wwj.domain.documentation.service.DocumentDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 文档服务实现类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Service
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentServiceImpl implements DocumentDomainService {

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    // 使用内存存储代替数据库，仅用于示例
    private final Map<String, Document> documentMap = new ConcurrentHashMap<>();

    @Override
    public Document createDocument(Document document) {
        if (document.getTitle() == null || document.getTitle().isEmpty()) {
            throw new BusinessException("文档标题不能为空");
        }

        // 生成ID
        document.setId(UUID.randomUUID().toString());
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        document.setCreateTime(now);
        document.setUpdateTime(now);
        // 默认为草稿状态
        document.setStatus(1);

        // 保存文档
        documentMap.put(document.getId(), document);
        logger.info("创建文档成功，ID: {}, 标题: {}", document.getId(), document.getTitle());

        return document;
    }

    @Override
    public Document getDocumentById(String id) {
        Document document = documentMap.get(id);
        if (document == null) {
            throw new BusinessException("文档不存在，ID: " + id);
        }
        logger.debug("获取文档，ID: {}, 标题: {}", document.getId(), document.getTitle());
        return document;
    }

    @Override
    public Document updateDocument(Document document) {
        if (document.getId() == null || !documentMap.containsKey(document.getId())) {
            throw new BusinessException("文档不存在，ID: " + document.getId());
        }

        // 获取原文档
        Document existingDocument = documentMap.get(document.getId());

        // 更新文档属性，保留不可变更的属性
        document.setCreateTime(existingDocument.getCreateTime());
        document.setUpdateTime(LocalDateTime.now());

        // 保存更新后的文档
        documentMap.put(document.getId(), document);
        logger.info("更新文档成功，ID: {}, 标题: {}", document.getId(), document.getTitle());

        return document;
    }

    @Override
    public void deleteDocument(String id) {
        if (!documentMap.containsKey(id)) {
            throw new BusinessException("文档不存在，ID: " + id);
        }

        documentMap.remove(id);
        logger.info("删除文档成功，ID: {}", id);
    }

    @Override
    public Document publishDocument(String id) {
        Document document = getDocumentById(id);
        document.publish();
        documentMap.put(id, document);
        logger.info("发布文档成功，ID: {}, 标题: {}", document.getId(), document.getTitle());
        return document;
    }

    @Override
    public Document archiveDocument(String id) {
        Document document = getDocumentById(id);
        document.archive();
        documentMap.put(id, document);
        logger.info("归档文档成功，ID: {}, 标题: {}", document.getId(), document.getTitle());
        return document;
    }

    @Override
    public List<Document> searchByTitle(String title) {
        return documentMap.values().stream()
                .filter(doc -> doc.getTitle() != null && doc.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> searchByContent(String content) {
        return documentMap.values().stream()
                .filter(doc -> doc.getContent() != null && doc.getContent().contains(content))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> searchByTag(String tag) {
        return documentMap.values().stream()
                .filter(doc -> doc.getTags() != null && doc.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> getAllDocuments() {
        return new ArrayList<>(documentMap.values());
    }

    @Override
    public List<Document> getPublishedDocuments() {
        return documentMap.values().stream()
                .filter(Document::isPublished)
                .collect(Collectors.toList());
    }
}
