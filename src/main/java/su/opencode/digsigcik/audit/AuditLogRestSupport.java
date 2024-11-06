package su.opencode.digsigcik.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;

@Aspect
@RequiredArgsConstructor
@Slf4j
// Временное решение, журналирование должно быть реализовано в отдельном сервисе, а соединенять с ним нужно по кафке
public class AuditLogRestSupport {

    @Value("${audit.default-user:Администратор}")
    private String username;

    private final AuditLogRestService auditLogRestService;

    @Pointcut("@annotation(su.opencode.digsigcik.audit.AuditLogThis)")
    public void annotatedMethodsProvider() {

    }

    @Around("annotatedMethodsProvider()")
    public Object logMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        AuditLogThis annotation = getAnnotation(proceedingJoinPoint);
        AuditLogEventEnum event = annotation.event();

        log.info("Logging: {}", annotation);

        // TODO: переработать парсинг/фильтр объектов
        String inputData = "{ \"status\": \"Done\" }";
        String outputData = "{ \"status\": \"Done\" }";

        boolean wasSuccessful = false;
        AuditSeverityEnum severity = AuditSeverityEnum.ERROR;
        long timeElapsed = 0L;

        try {
            timeElapsed = proceedMethodElapsingTime(proceedingJoinPoint);
            wasSuccessful = true;
            severity = AuditSeverityEnum.INFO;
        } catch (Throwable ex) {

            String msg = ex.getClass().getName() + " (" + ex.getMessage() + ")";
            log.warn("Неудачное исполнение операции: {}", msg);
            outputData = "Описание ошибки: " + msg;
            throw ex;

        } finally {
            auditLogRestService.sendLogEvent(
                    AuditLogEvent
                            .builder()
                            .login(username)
                            .event(event)
                            .severity(severity)
                            .wasSuccessful(wasSuccessful)
                            .inputData(inputData)
                            .resultData(outputData)
                            .timeElapsed(timeElapsed)
                            .build()
            );
        }


        return null;
    }

    private AuditLogThis getAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        return ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(AuditLogThis.class);
    }

    private long proceedMethodElapsingTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (getAnnotation(proceedingJoinPoint).markExecutionTime()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start(proceedingJoinPoint.hashCode() + "");
            proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            stopWatch.stop();
            return stopWatch.getTotalTimeMillis();
        } else {
            proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            return -1L;
        }
    }
}
