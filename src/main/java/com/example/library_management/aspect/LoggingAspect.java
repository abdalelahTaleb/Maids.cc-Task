package com.example.library_management.aspect;

import com.example.library_management.entity.AuditLog;
import com.example.library_management.repository.AuditLogRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Around("execution(* com.example.library_management.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        String exceptionMessage = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            exceptionMessage = e.getMessage();
            throw e;
        } finally {
            long elapsedTime = System.currentTimeMillis() - start;
            AuditLog logEntry = new AuditLog(
                    joinPoint.getSignature().toShortString(),
                    Arrays.toString(joinPoint.getArgs()),
                    result != null ? result.toString() : "void",
                    exceptionMessage
            );
            auditLogRepository.save(logEntry);
        }

        return result;
    }
}
