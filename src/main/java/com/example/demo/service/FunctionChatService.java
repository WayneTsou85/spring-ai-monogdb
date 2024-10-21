package com.example.demo.service;

import com.example.demo.dto.AIContent;
import com.example.demo.dto.QueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class FunctionChatService {

    private final ChatClient chatClient;

    /**
     * 使用classpath下的Prompt Template Files
     * @param clientBuilder
     * @param systemPromptTemplate
     */
    public FunctionChatService(ChatClient.Builder clientBuilder,
                               @Value("classpath:prompt/FunctionCallSystemPrompt.st") Resource systemPromptTemplate) {
        this.chatClient = clientBuilder
                .defaultSystem(systemPromptTemplate)
                .defaultFunctions("currentDateTimeFunction", "batchExeLogFunction")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public Flux<AIContent> chat(QueryDto query) {
        var content = chatClient.prompt()
                .user(query.getMessage())
                .call()
                .content();
        log.info("AI執行結果: {}", content);
        return Flux.just(new AIContent(content),
                new AIContent()
        );
    }
}
