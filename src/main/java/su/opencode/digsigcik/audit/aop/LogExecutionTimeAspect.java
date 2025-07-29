package su.opencode.digsigcik.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.opencode.digsigcik.audit.annotation.LogExecutionTime;
import su.opencode.digsigcik.audit.enums.LogLevel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Aspect
public class LogExecutionTimeAspect {
    @Around("@annotation(su.opencode.digsigcik.audit.annotation.LogExecutionTime)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;

            Logger logger = getLogger(joinPoint);

            LogLevel logLevel = getAnnotation(joinPoint).level();
            String info = getAnnotation(joinPoint).info();
            String urlOrMethod = extractUrlOrMethodName(joinPoint);

            String message = String.format("[%s] Execution of %s took %d ms. Thread: %s",
                    info.isEmpty() ? "No description" : info,
                    urlOrMethod,
                    duration,
                    Thread.currentThread().getName());

            logAtLevel(logger, logLevel, message);
        }
    }

    private static Logger getLogger(ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return LoggerFactory.getLogger(targetClass);
    }

    private static LogExecutionTime getAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(LogExecutionTime.class);
    }

    private String extractUrlOrMethodName(ProceedingJoinPoint joinPoint) {

        return Optional.ofNullable(joinPoint.getArgs())
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(arg -> arg instanceof String && ((String) arg).startsWith("http"))
                .map(String::valueOf)
                .findFirst()
                .orElseGet(() -> joinPoint.getSignature().getName());
    }

    private void logAtLevel(Logger logger, LogLevel level, String message) {
        switch (level) {
            case TRACE:
                logger.trace(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.info(message);
        }
    }
}
