package com.functionaldude.paperless_customGPT.rag.internal

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RagCleanupWorker(
  private val ragCleanupService: RagCleanupService,
) {
  private val log = LoggerFactory.getLogger(javaClass)

  // Run every five minutes
  @Scheduled(fixedDelayString = "PT5M")
  fun run() {
    var orphanedDocIds = ragCleanupService.findDeletedPaperlessDocIds(limit = 50)

    if (orphanedDocIds.isEmpty()) {
      log.info("Cleanup worker: nothing to delete")
      return
    }

    do {
      orphanedDocIds.forEach { paperlessDocId ->
        ragCleanupService.cleanupDocument(paperlessDocId)
      }

      orphanedDocIds = ragCleanupService.findDeletedPaperlessDocIds(limit = 50)
    } while (orphanedDocIds.isNotEmpty())
  }
}
