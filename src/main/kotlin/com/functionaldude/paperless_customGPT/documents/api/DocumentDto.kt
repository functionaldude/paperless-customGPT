package com.functionaldude.paperless_customGPT.documents.api

import java.time.LocalDate
import java.time.OffsetDateTime

data class DocumentDto(
  val id: Int,
  val title: String,
  val documentDate: LocalDate,
  val modifiedAt: OffsetDateTime?,
  val mimeType: String,
  val content: String,
  val ownerUsername: String?,
  val note: String?,
  val correspondentName: String?,
  val tags: List<String>?,
)