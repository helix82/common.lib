package su.opencode.digsigcik.specification.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnableDeepSearch {

    /*
     * Ставь эту аннотацию над всеми сложными объектами, если хочется искать по их подполям. Например, userProjection.login;
     *
     * Если этого не сделать, то при попытке поиска по "userProjection.login", userProjection будет (пытаться) конвертироваться в String и магии не произойдет.
     * В лучшем случае запрос на поиск будет проигнорирован.
     */
}
