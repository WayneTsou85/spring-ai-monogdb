package com.example.demo.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Function;

@Slf4j
@Component
@Description("取得當前時間")
public class CurrentDateTimeFunction implements Function<CurrentDateTimeFunction.Request, CurrentDateTimeFunction.Response> {

    @Override
    public Response apply(Request request) {
        log.info("取得當前時間, 傳入訊息: {}", request.message);
        return new Response(LocalDateTime.now());
    }

    public record Request(String message) {}
    public record Response(LocalDateTime dateTime) {}
}
