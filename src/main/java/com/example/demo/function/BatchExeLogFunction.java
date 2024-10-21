package com.example.demo.function;

import com.example.demo.dto.BatchExeLogDto;
import com.example.demo.repository.BatchExeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
@Description("取得批次執行紀錄")
public class BatchExeLogFunction implements Function<BatchExeLogFunction.Request, BatchExeLogFunction.Response> {

    private final BatchExeLogRepository exeLogRepository;

    @Override
    public Response apply(Request request) {
        var startTime = request.startTime;
        var endTime = request.endTime;
        log.info("查詢起訖日為 => Start Date: {}, End Date: {}", startTime, endTime);
        var exeLogDtos = exeLogRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime)
                .stream()
                .map(BatchExeLogDto::new)
                .toList();
        return new Response(exeLogDtos);
    }

    public record Request(Date startTime, Date endTime) {}

    public record Response(List<BatchExeLogDto> exeLogDtos) {}

}
