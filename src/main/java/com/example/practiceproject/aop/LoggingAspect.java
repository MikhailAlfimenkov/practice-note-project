package com.example.practiceproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.example.practiceproject.service..*(..))")
    public void noteServiceMethod() {
    }

    @Around("noteServiceMethod()")
    public Object serviceLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("Calling method: {} with arguments {}", methodName, Arrays.toString(methodArgs));

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long excecutionTime = System.currentTimeMillis() - startTime;
        log.info("Method: {} returned {}, executing time {} ms", methodName, result, excecutionTime);

        return result;
    }

}
