package com.ktr.izzi_learn_backend.config;


import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {
    @Bean
    ChatMemoryProvider chatMemoryProvider(Tokenizer tokenizer){
        return chatId-> MessageWindowChatMemory.withMaxMessages(10);
    }

   @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel){
        return new InMemoryEmbeddingStore<>();


           /* return PgVectorEmbeddingStore.builder()
                .host().port().database().user().password().table()
                .dropTableFirst(true)
                .dimension(embeddingModel.dimension())
                .build();*/
    }
        @Bean
ApplicationRunner  loadDocumentToVectorStore(
        ChatLanguageModel chatLanguageModel,
        EmbeddingModel embeddingModel,
        EmbeddingStore<TextSegment> embeddingStore,
        Tokenizer tokenizer,
        @Value("classpath:docs/sujet.pdf") Resource pdfRessource){
        return args ->{
            var doc = FileSystemDocumentLoader.loadDocument(pdfRessource.getFile().toPath());
            var ingestor = EmbeddingStoreIngestor.builder()
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .documentSplitter(DocumentSplitters.recursive(1000,100,tokenizer))
                    .build();
            DocumentParser parser= new ApachePdfBoxDocumentParser();
            Document document= parser.parse(pdfRessource.getInputStream());
            ingestor.ingest(document);
        };
}
@Bean
    ContentRetriever contentRetriever(EmbeddingModel embeddingModel,EmbeddingStore<TextSegment> embeddingStore){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(2)
                .minScore(0.6)
                .build();
    }

}
