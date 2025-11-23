package com.functionaldude.paperless_customGPT.documents.internal

import com.functionaldude.paperless.jooq.tables.references.*
import com.functionaldude.paperless_customGPT.documents.api.DocumentDto
import org.jooq.DSLContext
import org.jooq.impl.DSL.arrayAgg
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
        DOCUMENTS_DOCUMENT.MIME_TYPE,
        DOCUMENTS_DOCUMENT.CONTENT,
        AUTH_USER.USERNAME,
        DOCUMENTS_NOTE.NOTE,
        DOCUMENTS_CORRESPONDENT.NAME,
        arrayAgg(DOCUMENTS_TAG.NAME).`as`("tag_names"),
      )
      .from(DOCUMENTS_DOCUMENT)
      .leftJoin(AUTH_USER).on(DOCUMENTS_DOCUMENT.OWNER_ID.eq(AUTH_USER.ID))
      .leftJoin(DOCUMENTS_NOTE).on(DOCUMENTS_DOCUMENT.ID.eq(DOCUMENTS_NOTE.ID))
      .leftJoin(DOCUMENTS_CORRESPONDENT).on(DOCUMENTS_DOCUMENT.CORRESPONDENT_ID.eq(DOCUMENTS_CORRESPONDENT.ID))
      .leftJoin(DOCUMENTS_DOCUMENT_TAGS).on(DOCUMENTS_DOCUMENT_TAGS.DOCUMENT_ID.eq(DOCUMENTS_DOCUMENT.ID))
      .leftJoin(DOCUMENTS_TAG).on(DOCUMENTS_TAG.ID.eq(DOCUMENTS_DOCUMENT_TAGS.TAG_ID))
      .where(
        DOCUMENTS_DOCUMENT.MIME_TYPE.eq(PDF_MIME)
      )
      .groupBy(
        DOCUMENTS_DOCUMENT.ID,
        AUTH_USER.USERNAME,
        DOCUMENTS_NOTE.NOTE,
        DOCUMENTS_CORRESPONDENT.NAME,
      )
      .orderBy(DOCUMENTS_DOCUMENT.MODIFIED.desc())
      .fetch { record ->
        DocumentDto(
          id = record.get(DOCUMENTS_DOCUMENT.ID)!!,
          title = record.get(DOCUMENTS_DOCUMENT.TITLE) ?: "(untitled)",
          documentDate = record.get(DOCUMENTS_DOCUMENT.CREATED)!!,
          modifiedAt = record.get(DOCUMENTS_DOCUMENT.MODIFIED),
          mimeType = record.get(DOCUMENTS_DOCUMENT.MIME_TYPE)!!,
          content = record.get(DOCUMENTS_DOCUMENT.CONTENT)!!,
          ownerUsername = record.get(AUTH_USER.USERNAME),
          note = record.get(DOCUMENTS_NOTE.NOTE),
          correspondentName = record.get(DOCUMENTS_CORRESPONDENT.NAME),
          tags = record.get("tag_names", Array<String>::class.java)?.filterNotNull()?.toList() ?: emptyList(),
        )
      }
  }

  companion object {
    const val PDF_MIME = "application/pdf"
  }
}