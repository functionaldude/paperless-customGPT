package com.functionaldude.paperless_customGPT.rag.api

import com.functionaldude.paperless_customGPT.rag.internal.RagQueryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class RagQueryResponse(
  val results: List<RagSearchResult>
)

@RestController
@RequestMapping("/api/rag")
class RagController(
  private val ragService: RagQueryService,
) {

}