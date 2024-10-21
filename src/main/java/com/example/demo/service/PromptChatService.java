package com.example.demo.service;

import com.example.demo.dto.AIContent;
import com.example.demo.dto.QueryDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PromptChatService {

    private static final String SYSTEM_PROMPT_TEMPLATE =
            """
            你是一個文件助理，只能透過以下的資訊回答問題。
            依據詢問的問題給予建議或者是回答問題。
            如果不知道或者是使用者詢問的問題與資訊不相關，回答：我不知道Q_Q。

            {information}
            """;
    private static final String USER_PROMPT_TEMPLATE = "{query}";
    private static final String REFERENCE = "\n\n**參考資料來源**：%s";

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public PromptChatService(ChatClient.Builder clientBuilder,
                             VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        this.chatClient = clientBuilder
                .defaultSystem(SYSTEM_PROMPT_TEMPLATE)
                .defaultUser(USER_PROMPT_TEMPLATE)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public Flux<AIContent> chat(QueryDto query) {
        var message = query.getMessage();
        var documents = vectorStore.similaritySearch(message);
        log.info("關於{}, 查詢出相關的資料有{}筆", query, documents.size());
        var references = new HashSet<String>();
        var information = documents.stream()
                .peek(document -> recordReference(references, document))
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        var content = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.param("information", information))
                .user(promptUserSpec -> promptUserSpec.param("query", query))
                .call()
                .content();;
        log.info("AI執行結果: {}", content);
        return Flux.concat(
                Flux.just(new AIContent(content)),
                Flux.just(getReference(content, references))
        );
    }

    private void recordReference(HashSet<String> references, Document document) {
        String fileName = (String) document.getMetadata().get("file_name");
        if (StringUtils.isNotBlank(fileName)) {
            references.add(fileName);
        }
    }

    private AIContent getReference(String aiContent, HashSet<String> references) {
        if (StringUtils.contains(aiContent, "我不知道Q_Q")) {
            return new AIContent();
        }
        return new AIContent(REFERENCE.formatted(String.join(", ", references)), true);
    }
}
