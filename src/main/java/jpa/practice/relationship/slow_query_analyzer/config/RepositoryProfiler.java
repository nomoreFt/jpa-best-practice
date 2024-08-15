package jpa.practice.relationship.slow_query_analyzer.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class RepositoryProfiler {

    Logger logger = Logger.getLogger(RepositoryProfiler.class.getName());

    @Pointcut("execution(* jpa.practice.relationship.slow_query_analyzer.repository.*.*(..))")
    public void interceptRepositoryMethods() {
    }

    @Around("interceptRepositoryMethods()")
    public Object profile(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
        long elapsedTime = System.currentTimeMillis() - start;

        if (elapsedTime > 30) {
            logger.info("Method " + joinPoint.getSignature() + " executed in " + elapsedTime + "ms");
        }

        return result;
    }
}
