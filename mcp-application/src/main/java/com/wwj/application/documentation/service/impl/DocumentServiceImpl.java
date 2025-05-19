package com.wwj.application.documentation.service.impl;

import com.wwj.application.documentation.dto.DocumentDTO;
import com.wwj.application.documentation.service.DocumentService;
import com.wwj.domain.documentation.model.Document;
import com.wwj.domain.documentation.service.DocumentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档应用服务实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDomainService documentDomainService;

    @Override
    public DocumentDTO saveDocument(DocumentDTO documentDTO) {
        log.info("保存文档: {}", documentDTO.getTitle());
        
        // 转换为领域模型
        Document document = convertToDomain(documentDTO);
        
        // 调用领域服务
        Document savedDocument = documentDomainService.saveDocument(document);
        
        // 转换为DTO并返回
        return convertToDTO(savedDocument);
    }

    @Override
    public DocumentDTO getDocumentById(String id) {
        log.info("获取文档ID: {}", id);
        
        // 调用领域服务
        Document document = documentDomainService.getDocumentById(id);
        
        // 转换为DTO
        return convertToDTO(document);
    }

    @Override
    public List<DocumentDTO> findDocumentsByTitle(String title) {
        log.info("根据标题查找文档: {}", title);
        
        // 调用领域服务
        List<Document> documents = documentDomainService.findDocumentsByTitle(title);
        
        // 转换为DTO列表
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> searchDocuments(String keyword) {
        log.info("搜索文档关键词: {}", keyword);
        
        // 调用领域服务
        List<Document> documents = documentDomainService.searchDocuments(keyword);
        
        // 转换为DTO列表
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteDocument(String id) {
        log.info("删除文档ID: {}", id);
        
        // 调用领域服务
        return documentDomainService.deleteDocument(id);
    }
    
    /**
     * 将领域模型转换为DTO
     * 
     * @param document 文档领域模型
     * @return 文档DTO
     */
    private DocumentDTO convertToDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .content(document.getContent())
                .keywords(document.getKeywords())
                .createdTime(document.getCreatedTime())
                .updatedTime(document.getUpdatedTime())
                .author(document.getAuthor())
                .type(document.getType())
                .version(document.getVersion())
                .build();
    }
    
    /**
     * 将DTO转换为领域模型
     * 
     * @param dto 文档DTO
     * @return 文档领域模型
     */
    private Document convertToDomain(DocumentDTO dto) {
        Document document = new Document();
        document.setId(dto.getId());
        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setKeywords(dto.getKeywords());
        document.setCreatedTime(dto.getCreatedTime());
        document.setUpdatedTime(dto.getUpdatedTime());
        document.setAuthor(dto.getAuthor());
        document.setType(dto.getType());
        document.setVersion(dto.getVersion());
        return document;
    }
}