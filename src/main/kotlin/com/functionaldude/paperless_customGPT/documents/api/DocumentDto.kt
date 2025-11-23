package com.functionaldude.paperless_customGPT.documents.api

import java.time.LocalDate
import java.time.OffsetDateTime

data class DocumentDto(
  val id: Int,
  val title: String,
  val createdAt: LocalDate,
  val modifiedAt: OffsetDateTime?,
  val correspondentId: Int?
)