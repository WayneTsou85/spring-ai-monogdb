package com.example.demo.controller;

import com.example.demo.dto.AIContent;
import com.example.demo.dto.QueryDto;
import com.example.demo.service.FunctionChatService;
import com.example.demo.service.PromptChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RAGSearchController {

    private final PromptChatService promptChatService;
    private final FunctionChatService functionChatService;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AIContent> chat(@RequestBody QueryDto query) {
        return promptChatService.chat(query);
    }

    @PostMapping(value = "/functionChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AIContent> functionChat(@RequestBody QueryDto query) {
        return functionChatService.chat(query);
    }
}
