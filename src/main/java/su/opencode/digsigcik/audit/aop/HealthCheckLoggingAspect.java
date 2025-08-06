package su.opencode.digsigcik.audit.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import su.opencode.digsigcik.audit.*;

import static su.opencode.digsigcik.Constants.HEALTH_STATUS_UNKNOWN;

@Aspect
@Slf4j
public class HealthCheckLoggingAspect {
    private final String serviceName;

    @Value("${audit.default-user:Администратор}")
    private String username;

    private final AuditLogRestService auditLogRestService;

    public HealthCheckLoggingAspect(String serviceName, AuditLogRestService auditLogRestService) {
        this.serviceName = serviceName;
        this.auditLogRestService = auditLogRestService;
    }

    @AfterReturning(
            pointcut = "execution(* org.springframework.boot.actuate.health.HealthIndicator+.getHealth(..))",
            returning = "health"
    )
    public void logHealthStatus(Health health) {
        String healthDB = health.toString();
        Boolean status = null;
        String outputData = "{}";
        String inputData = "{}";
        long timeElapsed = -1L;
        long dataSize = -1L;
        AuditLogEventEnum event = AuditLogEventEnum.NA;
        AuditSeverityEnum severity = AuditSeverityEnum.ERROR;
        boolean wasSuccessful = false;
        if (healthDB.contains("database=")) {
            if (healthDB.startsWith("UP")) {
                status = true;
            } else if (healthDB.startsWith("DOWN")) {
                status = false;
            }
            if (status == null) {
                status = false;
                outputData = HEALTH_STATUS_UNKNOWN;
            } else {
                outputData = healthDB;
            }
            switch (serviceName) {
                case "M4D":
                    event = AuditLogEventEnum.HEALTH_M4D;
                    break;
                case "SYNAPI":
                    event = AuditLogEventEnum.HEALTH_SYNAPI;
                    break;
                case "REFBOOK":
                    event = AuditLogEventEnum.HEALTH_REFBOOK;
                    break;
                case "TLS":
                    event = AuditLogEventEnum.HEALTH_TLS;
                    break;
                case "PVV_ADAPTER":
                    event = AuditLogEventEnum.HEALTH_PVV_ADAPTER;
                    break;
                default:
            }
            if (Boolean.TRUE.equals(status)) {
                severity = AuditSeverityEnum.INFO;
                wasSuccessful = true;
            }
            log.info(healthDB);
            log.info(serviceName);
            auditLogRestService.trySendLogEvent(
                    AuditLogEvent
                            .builder()
                            .login(username)
                            .event(event)
                            .severity(severity)
                            .wasSuccessful(wasSuccessful)
                            .inputData(inputData)
                            .resultData(outputData)
                            .timeElapsed(timeElapsed)
                            .payloadSize(dataSize)
                            .build()
            );
        }
    }
}
