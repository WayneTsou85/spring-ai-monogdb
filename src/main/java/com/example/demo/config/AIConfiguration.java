package com.example.demo.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.MongoDBAtlasVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AIConfiguration {
    @Value("${spring.ai.openai.api-key}")
    private String openAiKey;

    @Value("${spring.ai.vectorstore.mongodb.initialize-schema}")
    private Boolean initSchema;

    @Bean
    public EmbeddingModel embeddingModel() {
        return new OpenAiEmbeddingModel(new OpenAiApi(openAiKey));
    }

    @Bean
    public VectorStore mongodbVectorStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel,
                MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.builder().build(), initSchema);
    }
}
