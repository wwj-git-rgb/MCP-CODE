package com.wwj.infrastructure.documentation.service;

import com.wwj.common.exception.BusinessException;
import com.wwj.domain.documentation.model.Document;
import com.wwj.domain.documentation.service.DocumentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 文档领域服务实现
 * 注意：这是一个内存实现，实际项目中应使用数据库
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentDomainServiceImpl implements DocumentDomainService {

    // 使用内存Map模拟文档存储
    private final Map<String, Document> documentStore = new HashMap<>();

    @Override
    public Document saveDocument(Document document) {
        log.info("保存文档: {}", document.getTitle());
        
        LocalDateTime now = LocalDateTime.now();
        
        // 新文档
        if (document.getId() == null || document.getId().isEmpty()) {
            document.setId(UUID.randomUUID().toString());
            document.setCreatedTime(now);
            document.setVersion(1);
        } 
        // 更新文档
        else {
            Document existingDocument = getDocumentById(document.getId());
            document.setCreatedTime(existingDocument.getCreatedTime());
            document.setVersion(existingDocument.getVersion() + 1);
        }
        
        document.setUpdatedTime(now);
        documentStore.put(document.getId(), document);
        
        return document;
    }

    @Override
    public Document getDocumentById(String id) {
        log.info("获取文档ID: {}", id);
        
        Document document = documentStore.get(id);
        if (document == null) {
            throw new BusinessException("文档不存在：" + id);
        }
        
        return document;
    }

    @Override
    public List<Document> findDocumentsByTitle(String title) {
        log.info("根据标题查找文档: {}", title);
        
        return documentStore.values().stream()
                .filter(doc -> doc.getTitle() != null && doc.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> searchDocuments(String keyword) {
        log.info("搜索文档关键词: {}", keyword);
        
        if (keyword == null || keyword.isEmpty()) {
            return new ArrayList<>(documentStore.values());
        }
        
        return documentStore.values().stream()
                .filter(doc -> containsKeyword(doc, keyword))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteDocument(String id) {
        log.info("删除文档ID: {}", id);
        
        if (!documentStore.containsKey(id)) {
            return false;
        }
        
        documentStore.remove(id);
        return true;
    }
    
    private boolean containsKeyword(Document document, String keyword) {
        keyword = keyword.toLowerCase();
        
        return (document.getTitle() != null && document.getTitle().toLowerCase().contains(keyword)) ||
               (document.getContent() != null && document.getContent().toLowerCase().contains(keyword)) ||
               (document.getKeywords() != null && document.getKeywords().toLowerCase().contains(keyword));
    }
}