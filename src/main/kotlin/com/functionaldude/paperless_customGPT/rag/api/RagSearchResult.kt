package com.functionaldude.paperless_customGPT.rag.api

data class RagSearchResult(
  val paperlessDocId: Int,
  val title: String?,
  val correspondentName: String?,
  val snippet: String,
  val score: Double,
)