package su.opencode.digsigcik.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@RequiredArgsConstructor
@Slf4j
// Временное решение, журналирование должно быть реализовано в отдельном сервисе, а соединенять с ним нужно по кафке
public class AuditLogRestSupport {

    @Value("${audit.default-user:Администратор}")
    private String username;

    private final AuditLogRestService auditLogRestService;

    private final AuditDataSerializer auditDataSerializer = new AuditDataSerializer(new AuditDataFilter());

    @Pointcut("@annotation(su.opencode.digsigcik.audit.AuditLogThis)")
    public void annotatedMethodsProvider() {

    }

    @Around("annotatedMethodsProvider()")
    public Object logMethod(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        AuditLogThis annotation = getAnnotation(proceedingJoinPoint);
        AuditLogEventEnum event = annotation.event();

        log.info("Logging: {}", annotation);

        // TODO: переработать парсинг/фильтр объектов
//        String inputData = "{ \"status\": \"Done\" }";
        String inputData = serialize(proceedingJoinPoint.getArgs());
        String outputData = "{ \"status\": \"Done\" }";

        boolean wasSuccessful = false;
        AuditSeverityEnum severity = AuditSeverityEnum.ERROR;
        long timeElapsed = 0L;

        try {
            timeElapsed = proceedMethodElapsingTime(proceedingJoinPoint);
            wasSuccessful = true;
            severity = AuditSeverityEnum.INFO;
            outputData = serialize(resultCache);

            // FIXME: see tls.service:OrderController:createOrder/createOrderJson
            // FIXME: see tls.service:AcmeServiceImp:orderInfo
            if (event.getTopic() == AuditLogTopicEnum.TLS_CERTS_ORDER && resultCache instanceof ResponseEntity && ((ResponseEntity<?>) resultCache).getBody() instanceof Map)
            {

                String provider = "";
                try {
                    log.info("Attempting logging TLS_CERTS_ORDER topic event using provider {}", provider);
                    provider = (String) ((Map<?, ?>) ((ResponseEntity<?>) resultCache).getBody()).get("provider");
                } catch (Exception ex) {
                    log.warn("Не был найден provider в результирующей мапе: {}", ex.getMessage());
                }

                switch (provider) {
                    case "ЦС СЭП (Step CA)":
                        event = AuditLogEventEnum.TLS_CERTS_ORDER_STEP_CA;
                        break;
                    case "НУЦ Минцифры":
                        event = AuditLogEventEnum.TLS_CERTS_ORDER_NUC;
                        break;
                    case "УЦ ФК":
                        event = AuditLogEventEnum.TLS_CERTS_ORDER_FK;
                        break;
                    default:
                        event = AuditLogEventEnum.TLS_CERTS_ORDER_OTHER_CA;
                }
            }
            // FIXME: see tls.service:CertificateController:certInfo(Certificate certificate)
            if (event.equals(AuditLogEventEnum.TLS_SERV_CERTS_UPLOAD) && resultCache instanceof ResponseEntity && ((ResponseEntity<?>) resultCache).getBody() instanceof Map)
            {

                String provider = "";
                try {
                    log.info("Attempting logging TLS_SERV_CERTS_UPLOAD topic event using provider {}", provider);
                    provider = (String) ((Map<?, ?>) ((ResponseEntity<?>) resultCache).getBody()).get("provider");
                } catch (Exception ex) {
                    log.warn("Не был найден provider в результирующей мапе: {}", ex.getMessage());
                }

                switch (provider) {
                    case "ЦС СЭП (Step CA)":
                        event = AuditLogEventEnum.TLS_SERV_CERTS_STEP_CA_CREATE;
                        break;
                    case "НУЦ Минцифры":
                        event = AuditLogEventEnum.TLS_SERV_CERTS_NUC_CREATE;
                        break;
                    case "УЦ ФК":
                        event = AuditLogEventEnum.TLS_SERV_CERTS_FK_CREATE;
                        break;
                    default:
                        event = AuditLogEventEnum.TLS_SERV_CERTS_OTHER_CA_CREATE;
                }
            }

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

    Object resultCache;

    private long proceedMethodElapsingTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        resultCache = null;

        if (getAnnotation(proceedingJoinPoint).markExecutionTime()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start(proceedingJoinPoint.hashCode() + "");
            resultCache = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            stopWatch.stop();
            return stopWatch.getTotalTimeMillis();
        } else {
            resultCache = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            return -1L;
        }
    }

    // FIXME: refactoooor и фильтрация полей (Данияр)
    private String serialize(Object object) {

        if (object instanceof Map)
            return auditDataSerializer.tryParseMap((Map) object);

        return serialize(new Object[] { object });
    }

    private String serialize(Object[] objects) {
        try {
            var filtered = Arrays.stream(objects)
                    .filter(object -> !(object instanceof String))
                    .collect(Collectors.toList());

            return StringEscapeUtils.unescapeJava(auditDataSerializer.toJson(filtered));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return "{}";
        }
    }
}
