package su.opencode.digsigcik.tsl.annotation;

import org.springframework.context.annotation.Import;
import su.opencode.digsigcik.tsl.autowiring.TslParserConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(TslParserConfig.class)
public @interface EnableTslParserHandling {
}
