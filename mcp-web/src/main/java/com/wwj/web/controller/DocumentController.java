package com.wwj.web.controller;

import com.wwj.application.documentation.dto.DocumentDTO;
import com.wwj.application.documentation.service.DocumentService;
import com.wwj.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档控制器
 *
 * @author wenjie
 * @since 1.0.0
 */
@Tag(name = "文档控制器", description = "提供文档管理相关接口")
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "创建或更新文档", description = "创建新文档或更新现有文档")
    @PostMapping
    public Result<DocumentDTO> saveDocument(@Validated @RequestBody DocumentDTO documentDTO) {
        DocumentDTO savedDocument = documentService.saveDocument(documentDTO);
        return Result.success(savedDocument);
    }

    @Operation(summary = "获取文档", description = "根据ID获取文档详情")
    @GetMapping("/{id}")
    public Result<DocumentDTO> getDocument(
            @Parameter(description = "文档ID", required = true)
            @PathVariable String id) {
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        return Result.success(documentDTO);
    }

    @Operation(summary = "根据标题查询文档", description = "根据标题关键字查询文档")
    @GetMapping("/by-title")
    public Result<List<DocumentDTO>> findDocumentsByTitle(
            @Parameter(description = "文档标题关键字", required = true)
            @RequestParam String title) {
        List<DocumentDTO> documents = documentService.findDocumentsByTitle(title);
        return Result.success(documents);
    }

    @Operation(summary = "搜索文档", description = "根据关键字全文搜索文档")
    @GetMapping("/search")
    public Result<List<DocumentDTO>> searchDocuments(
            @Parameter(description = "搜索关键字")
            @RequestParam(required = false) String keyword) {
        List<DocumentDTO> documents = documentService.searchDocuments(keyword);
        return Result.success(documents);
    }

    @Operation(summary = "删除文档", description = "根据ID删除文档")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDocument(
            @Parameter(description = "文档ID", required = true)
            @PathVariable String id) {
        boolean result = documentService.deleteDocument(id);
        return Result.success(result);
    }
}