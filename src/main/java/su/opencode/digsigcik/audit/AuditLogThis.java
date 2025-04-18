package su.opencode.digsigcik.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Order(Integer.MIN_VALUE) // TODO: see Priority
public @interface AuditLogThis {

    static final Class<?> NULL = Class.class;


    AuditLogEventEnum event() default AuditLogEventEnum.NA;

    AuditSeverityEnum severityWhenSuccess() default AuditSeverityEnum.INFO;

    AuditSeverityEnum severityWhenFail() default AuditSeverityEnum.ERROR;

    boolean markExecutionTime() default false;

    Class<?> deleteByIdRepository() default Class.class;
}
