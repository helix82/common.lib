package su.opencode.digsigcik.specification.annotation;

import org.springframework.context.annotation.Import;
import su.opencode.digsigcik.specification.autowiring.SpecificationConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SpecificationConfig.class)
public @interface EnableSpecificationHandling {
}
