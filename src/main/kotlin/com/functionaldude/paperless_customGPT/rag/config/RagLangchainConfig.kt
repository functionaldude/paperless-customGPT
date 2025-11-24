package com.functionaldude.paperless_customGPT.rag.config

import dev.langchain4j.data.document.DocumentParser
import dev.langchain4j.data.document.DocumentSplitter
import dev.langchain4j.data.document.parser.TextDocumentParser
import dev.langchain4j.data.document.splitter.DocumentSplitters
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder
import dev.langchain4j.model.embedding.EmbeddingModel
import dev.langchain4j.model.openai.OpenAiEmbeddingModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.http.HttpClient
import java.time.Duration

@Configuration
class RagLangchainConfig {

  @Bean
  fun embeddingModel(
    @Value("http://localhost:1234/v1") baseUrl: String,
    @Value("text-embedding-multilingual-e5-base") modelName: String,
  ): EmbeddingModel {
    return OpenAiEmbeddingModel.builder()
      .baseUrl(baseUrl)
      .apiKey("lm-studio") // dummy key because of the required field
      .modelName(modelName)
      .timeout(Duration.ofSeconds(60))
      .httpClientBuilder(
        JdkHttpClientBuilder()
          .httpClientBuilder(
            HttpClient.newBuilder()
              .version(HttpClient.Version.HTTP_1_1) // Default is HTTP2 -> LM Studio does not support HTTP2 yet
          )
      )
      .logRequests(false)
      .logResponses(false)
      .build()
  }

  @Bean
  fun documentParser(): DocumentParser = TextDocumentParser()

  @Bean
  fun documentSplitter(): DocumentSplitter {
    return DocumentSplitters.recursive(
      1000, // max chunk chars
      200   // overlap chars
    )
  }
}