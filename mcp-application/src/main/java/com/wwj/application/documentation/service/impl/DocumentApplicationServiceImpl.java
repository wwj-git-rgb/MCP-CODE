package com.wwj.application.documentation.service.impl;

import com.wwj.application.documentation.dto.DocumentDTO;
import com.wwj.application.documentation.service.DocumentApplicationService;
import com.wwj.domain.documentation.model.Document;
import com.wwj.domain.documentation.service.DocumentDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文档应用服务实现
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Service
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentApplicationServiceImpl implements DocumentApplicationService {

    private final Logger logger = LoggerFactory.getLogger(DocumentApplicationServiceImpl.class);

    @Autowired
    private DocumentDomainService documentDomainService;

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        logger.info("创建文档: {}", documentDTO.getTitle());
        Document document = convertToEntity(documentDTO);
        document = documentDomainService.createDocument(document);
        return convertToDTO(document);
    }

    @Override
    public DocumentDTO getDocumentById(String id) {
        logger.info("获取文档: {}", id);
        Document document = documentDomainService.getDocumentById(id);
        return convertToDTO(document);
    }

    @Override
    public DocumentDTO updateDocument(DocumentDTO documentDTO) {
        logger.info("更新文档: {}", documentDTO.getId());
        Document document = convertToEntity(documentDTO);
        document = documentDomainService.updateDocument(document);
        return convertToDTO(document);
    }

    @Override
    public void deleteDocument(String id) {
        logger.info("删除文档: {}", id);
        documentDomainService.deleteDocument(id);
    }

    @Override
    public DocumentDTO publishDocument(String id) {
        logger.info("发布文档: {}", id);
        Document document = documentDomainService.publishDocument(id);
        return convertToDTO(document);
    }

    @Override
    public DocumentDTO archiveDocument(String id) {
        logger.info("归档文档: {}", id);
        Document document = documentDomainService.archiveDocument(id);
        return convertToDTO(document);
    }

    @Override
    public List<DocumentDTO> searchDocuments(String keyword) {
        logger.info("搜索文档，关键词: {}", keyword);
        Set<Document> resultSet = new HashSet<>();
        
        // 搜索标题
        resultSet.addAll(documentDomainService.searchByTitle(keyword));
        
        // 搜索内容
        resultSet.addAll(documentDomainService.searchByContent(keyword));
        
        // 搜索标签
        resultSet.addAll(documentDomainService.searchByTag(keyword));
        
        return resultSet.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getAllDocuments() {
        logger.info("获取所有文档");
        List<Document> documents = documentDomainService.getAllDocuments();
        return convertToDTOList(documents);
    }

    @Override
    public List<DocumentDTO> getPublishedDocuments() {
        logger.info("获取已发布文档");
        List<Document> documents = documentDomainService.getPublishedDocuments();
        return convertToDTOList(documents);
    }

    /**
     * 将领域模型转换为DTO
     *
     * @param document 文档领域模型
     * @return 文档DTO
     */
    private DocumentDTO convertToDTO(Document document) {
        if (document == null) {
            return null;
        }

        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setTitle(document.getTitle());
        dto.setContent(document.getContent());
        dto.setType(document.getType());
        dto.setCreator(document.getCreator());
        dto.setTags(document.getTags());
        dto.setStatus(document.getStatus());

        // 格式化时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (document.getCreateTime() != null) {
            dto.setCreateTime(document.getCreateTime().format(formatter));
        }
        if (document.getUpdateTime() != null) {
            dto.setUpdateTime(document.getUpdateTime().format(formatter));
        }

        // 状态名称
        switch (document.getStatus()) {
            case 1:
                dto.setStatusName("草稿");
                break;
            case 2:
                dto.setStatusName("已发布");
                break;
            case 3:
                dto.setStatusName("已归档");
                break;
            default:
                dto.setStatusName("未知");
        }

        return dto;
    }

    /**
     * 将DTO转换为领域模型
     *
     * @param dto 文档DTO
     * @return 文档领域模型
     */
    private Document convertToEntity(DocumentDTO dto) {
        if (dto == null) {
            return null;
        }

        Document document = new Document();
        document.setId(dto.getId());
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setType(dto.getType());
        document.setCreator(dto.getCreator());
        document.setTags(dto.getTags());
        document.setStatus(dto.getStatus());

        // 尝试解析时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            if (dto.getCreateTime() != null && !dto.getCreateTime().isEmpty()) {
                document.setCreateTime(LocalDateTime.parse(dto.getCreateTime(), formatter));
            }
            if (dto.getUpdateTime() != null && !dto.getUpdateTime().isEmpty()) {
                document.setUpdateTime(LocalDateTime.parse(dto.getUpdateTime(), formatter));
            }
        } catch (Exception e) {
            logger.warn("解析时间失败: {}", e.getMessage());
            // 使用当前时间作为默认值
            LocalDateTime now = LocalDateTime.now();
            if (document.getCreateTime() == null) {
                document.setCreateTime(now);
            }
            if (document.getUpdateTime() == null) {
                document.setUpdateTime(now);
            }
        }

        return document;
    }

    /**
     * 将领域模型列表转换为DTO列表
     *
     * @param documents 文档领域模型列表
     * @return 文档DTO列表
     */
    private List<DocumentDTO> convertToDTOList(List<Document> documents) {
        if (documents == null) {
            return new ArrayList<>();
        }

        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
