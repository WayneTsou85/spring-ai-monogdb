package com.example.demo.aop;

import com.example.demo.dto.QueryDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerAOP {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void pointcutController() {
    }

    @Before("pointcutController()")
    public void beforeRestControllerMethodExecution(JoinPoint joinPoint) {
        var request = (QueryDto) joinPoint.getArgs()[0];
        log.info("使用者查詢: {}", request);
    }
}
