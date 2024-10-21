package su.opencode.digsigcik.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowAudit {

    /*
     * Принудительно вставлять аннотируемое поле в журнал аудита при сериализации (@AllowLogThis).
     * Предпочтительный способ работы с журналом аудита.
     *
     * TODO: see @Target({ElementType.FIELD, ElementType.PARAMETER})
     */

}
