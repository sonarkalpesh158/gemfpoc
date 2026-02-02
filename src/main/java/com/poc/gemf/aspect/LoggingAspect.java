package com.poc.gemf.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut that targets all methods within the Service package.
     */
    @Pointcut("execution(* com.poc.gemf.service..*(..))")
    public void serviceLayer() {
    }

    /**
     * Runs before the method execution.
     * Logs the Method Name and Arguments.
     */
    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("ENTERING: {}() with arguments: {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Runs after the method returns successfully.
     * Logs the Result.
     */
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info(" EXITING: {}() with result: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    /**
     * Runs if the method throws an exception.
     * Logs the Exception message.
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("EXCEPTION in: {}() Message: {}",
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }
}
