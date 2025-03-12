package com.eazybytes.eazyschool.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.eazybytes.eazyschool..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info(joinPoint.getSignature().toString()+" Method Start");
        Instant start = Instant.now();
        Object proceed = joinPoint.proceed();
        Instant end = Instant.now();
        log.info(joinPoint.getSignature().toString()+" Method end");
        long timeElapsed = Duration.between(start,end).toMillis();
        log.info(joinPoint.getSignature().toString()+" Method Duration is: "+timeElapsed);
        return proceed;
    }

    @AfterThrowing(value = "execution(* com.eazybytes.eazyschool..*.*(..))", throwing = "e")
    public void logException(JoinPoint joinpoint, Exception e){
        log.error(joinpoint.getSignature().toString()+"Exception occured due to "+e.getMessage());
    }
}
