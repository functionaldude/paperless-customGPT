package com.functionaldude.paperless_customGPT.documents.internal

import com.functionaldude.paperless.jooq.tables.references.DOCUMENTS_DOCUMENT
import com.functionaldude.paperless_customGPT.documents.api.DocumentDto
import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class PaperlessDocumentService(
  private val dsl: DSLContext
) {
  fun findAllDocuments(): List<DocumentDto> {
    return dsl
      .select(
        DOCUMENTS_DOCUMENT.ID,
        DOCUMENTS_DOCUMENT.TITLE,
        DOCUMENTS_DOCUMENT.CREATED,
        DOCUMENTS_DOCUMENT.MODIFIED,
        DOCUMENTS_DOCUMENT.OWNER_ID,
        DOCUMENTS_DOCUMENT.MIME_TYPE,
      )
      .from(DOCUMENTS_DOCUMENT)
      .orderBy(DOCUMENTS_DOCUMENT.MODIFIED.desc())
      .fetch { record ->
        DocumentDto(
          id = record.get(DOCUMENTS_DOCUMENT.ID)!!,
          title = record.get(DOCUMENTS_DOCUMENT.TITLE) ?: "(untitled)",
          createdAt = record.get(DOCUMENTS_DOCUMENT.CREATED)!!,
          modifiedAt = record.get(DOCUMENTS_DOCUMENT.MODIFIED),
          correspondentId = record.get(DOCUMENTS_DOCUMENT.OWNER_ID),
        )
      }

  }
}