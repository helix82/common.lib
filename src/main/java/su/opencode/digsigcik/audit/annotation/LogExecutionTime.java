package su.opencode.digsigcik.audit.annotation;

import su.opencode.digsigcik.audit.enums.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
    String info() default "";
    LogLevel level() default LogLevel.INFO;
}
