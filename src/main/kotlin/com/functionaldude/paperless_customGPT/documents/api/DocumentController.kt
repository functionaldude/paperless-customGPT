package com.functionaldude.paperless_customGPT.documents.api

import com.functionaldude.paperless_customGPT.documents.internal.PaperlessDocumentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/documents")
class DocumentController(
  private val paperlessDocumentService: PaperlessDocumentService
) {
  @RequestMapping
  fun listDocuments(): List<DocumentDto> {
    return paperlessDocumentService.findAllDocuments()
  }
}